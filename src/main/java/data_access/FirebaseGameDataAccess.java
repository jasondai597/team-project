package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;

import entity.Game;
import use_case.game.GameDataAccess;

public class FirebaseGameDataAccess implements GameDataAccess {

    private final CollectionReference gamesRef;

    public FirebaseGameDataAccess() {
        FirebaseInitialize.initFirebase();
        Firestore db = FirestoreClient.getFirestore();
        gamesRef = db.collection("SavedGames");
    }

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void save(Game game) {
        Map<String, Object> data = new HashMap<>();
        data.put("board", boardToList(game.getBoard()));  // <== convert here
        data.put("difficulty", game.getDifficulty());
        data.put("mode", game.getMode());
        data.put("elapsedMs", game.getElapsedMs());
        data.put("timestamp", System.currentTimeMillis());

        gamesRef.document(game.getId()).set(data);
    }

    @Override
    public Optional<Game> load(String gameId) {
        try {
            DocumentSnapshot snap = gamesRef.document(gameId).get().get();
            if (!snap.exists()) return Optional.empty();

            int[][] board = listToBoard((List<List<Long>>) snap.get("board"));
            String difficulty = snap.getString("difficulty");
            String mode = snap.getString("mode");
            Long elapsed = snap.getLong("elapsedMs");

            return Optional.of(new Game(
                    gameId,
                    board,
                    difficulty,
                    mode,
                    elapsed == null ? 0L : elapsed
            ));
        } catch (InterruptedException | ExecutionException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Game> listAll() {
        try {
            ApiFuture<QuerySnapshot> future =
                    gamesRef.orderBy("timestamp", Query.Direction.DESCENDING).get();
            List<QueryDocumentSnapshot> docs = future.get().getDocuments();

            List<Game> games = new ArrayList<>();
            for (QueryDocumentSnapshot doc : docs) {
                int[][] board = listToBoard((List<List<Long>>) doc.get("board"));
                String difficulty = doc.getString("difficulty");
                String mode = doc.getString("mode");
                Long elapsed = doc.getLong("elapsedMs");

                games.add(new Game(
                        doc.getId(),
                        board,
                        difficulty,
                        mode,
                        elapsed == null ? 0L : elapsed
                ));
            }
            return games;
        } catch (InterruptedException | ExecutionException e) {
            return List.of();
        }
    }


    @Override
    public void delete(String gameId) {
        try {
            System.out.println("FirebaseGameDataAccess: Deleting game with ID: " + gameId);
            gamesRef.document(gameId).delete().get();
            System.out.println("FirebaseGameDataAccess: Successfully deleted game: " + gameId);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("FirebaseGameDataAccess: Error deleting game: " + gameId);
            e.printStackTrace();
        }
    }

    private List<List<Long>> boardToList(int[][] board) {
        List<List<Long>> outer = new ArrayList<>();
        for (int r = 0; r < board.length; r++) {
            List<Long> row = new ArrayList<>();
            for (int c = 0; c < board[r].length; c++) {
                row.add((long) board[r][c]);
            }
            outer.add(row);
        }
        return outer;
    }

    private int[][] listToBoard(List<List<Long>> data) {
        if (data == null) return new int[9][9];
        int rows = data.size();
        int[][] board = new int[rows][9];
        for (int r = 0; r < rows; r++) {
            List<Long> row = data.get(r);
            for (int c = 0; c < row.size(); c++) {
                Long v = row.get(c);
                board[r][c] = v == null ? 0 : v.intValue();
            }
        }
        return board;
    }
}

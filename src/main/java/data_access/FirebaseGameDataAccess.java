package data_access;

import com.google.cloud.firestore.*;
import com.google.api.core.ApiFuture;
import com.google.firebase.cloud.FirestoreClient;
import entity.Game;
import use_case.game.GameDataAccess;

import java.util.*;
import java.util.concurrent.ExecutionException;

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
        data.put("board", game.getBoard());
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

            int[][] board = snap.get("board", int[][].class);
            String difficulty = snap.getString("difficulty");
            String mode = snap.getString("mode");
            long elapsed = snap.getLong("elapsedMs");

            return Optional.of(new Game(gameId, board, difficulty, mode, elapsed));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Game> listAll() {
        try {
            List<QueryDocumentSnapshot> docs =
                    gamesRef.orderBy("timestamp", Query.Direction.DESCENDING).get().get().getDocuments();

            List<Game> games = new ArrayList<>();
            for (QueryDocumentSnapshot doc : docs) {
                int[][] board = doc.get("board", int[][].class);
                String difficulty = doc.getString("difficulty");
                String mode = doc.getString("mode");
                long elapsed = doc.getLong("elapsedMs");

                games.add(new Game(doc.getId(), board, difficulty, mode, elapsed));
            }
            return games;

        } catch (Exception e) {
            return List.of();
        }
    }
}

package data_access;

import com.google.cloud.firestore.*;

import com.google.api.core.ApiFuture;
import com.google.firebase.cloud.FirestoreClient;

import java.util.concurrent.ExecutionException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
public class LeaderBoardDAO {

    private final CollectionReference leaderboardRef;
    private final Firestore db;

    public LeaderBoardDAO() {
        FirebaseInitialize.initFirebase();
        this.db = FirestoreClient.getFirestore();
        this.leaderboardRef = db.collection("Leaderboard");
    }

    public void addScore(String playerId, int timetaken) {
        Map<String, Object> data = new java.util.HashMap<>();
        data.put("Time", timetaken);
        data.put("timestamp", System.currentTimeMillis());
        data.put("playerId", playerId);

        ApiFuture<WriteResult> future = leaderboardRef.document(playerId).set(data);
        try {
            future.get();
            System.out.println("Score added for " + playerId);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error adding score: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Map<String, Object>> getLeaderboard() {
        ApiFuture<QuerySnapshot> future = leaderboardRef.orderBy("score",
                com.google.cloud.firestore.Query.Direction.DESCENDING).get();
        List<Map<String, Object>> leaderboardData = new ArrayList<>();
        try {
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            for (QueryDocumentSnapshot document : documents) {
                leaderboardData.add(document.getData());
            }
            return leaderboardData;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving leaderboard: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}

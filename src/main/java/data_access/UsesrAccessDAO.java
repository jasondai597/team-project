package data_access;

import com.google.cloud.firestore.*;

import com.google.api.core.ApiFuture;
import com.google.firebase.cloud.FirestoreClient;

import java.util.concurrent.ExecutionException;
import java.util.Map;
public class UsesrAccessDAO {

    private final CollectionReference userRef;
    private final Firestore db;

    public UsesrAccessDAO() {
        FirebaseInitialize.initFirebase();
        this.db = FirestoreClient.getFirestore();
        this.userRef = db.collection("Users");
    }

    public void adduser(String username, String password) {
        Map<String, Object> data = new java.util.HashMap<>();
        data.put("password", password);

        ApiFuture<WriteResult> future = userRef.document(username).set(data);
        try {
            future.get();
            System.out.println("User added: " + username);
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error adding score: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean checkForUser(String username) {
        DocumentReference docRef = userRef.document(username);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        boolean exist;
        try {
            DocumentSnapshot snapshot = future.get();
            exist = snapshot.exists();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("Fail to communicate with firebase");
        }
        return exist;
    }
}

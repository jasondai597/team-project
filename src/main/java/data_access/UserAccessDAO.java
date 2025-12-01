package data_access;

import com.google.cloud.firestore.*;

import com.google.api.core.ApiFuture;
import com.google.firebase.cloud.FirestoreClient;

import java.util.concurrent.ExecutionException;
import java.util.Map;

//import classes from entity
import entity.User;
import entity.UserFactory;
import use_case.login.LoginUserDataAccess;
import use_case.signUp.SignUpUserDataAccessInterface;

public class UserAccessDAO implements SignUpUserDataAccessInterface, LoginUserDataAccess {

    private final CollectionReference userRef;
    private final Firestore db;

    public UserAccessDAO() {
        FirebaseInitialize.initFirebase();
        this.db = FirestoreClient.getFirestore();
        this.userRef = db.collection("Users");
    }

    @Override
    public void addUser(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
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

    @Override
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

    @Override
    public boolean validateUser(String username, String password) {
        final User user = getUser(username, new UserFactory());
        return user.getPassword().equals(password);
    }

    // add a score given the type (unranked or ranked + difficulty) ex: unranked hard, ranked easy
    // Time will be an object for now. Can be changed later.
    public void add_score(String username, Object time, String type) {
        Map <String, Object> data = new java.util.HashMap<>();
        data.put(type, time);
        ApiFuture<WriteResult> future = userRef.document(username).update(data);
        try {
            future.get();
            System.out.println(type + ": " + time + "time added for " + username);
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("User doesn't exist, or failed to communicate with firebase");
        }
    }
    public Object get_score(String username, String type) {
        ApiFuture<DocumentSnapshot> future = userRef.document(username).get();
        try {
            DocumentSnapshot snapshot = future.get();
            return snapshot.get(type); //Returns null if the field doesn't exist'
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("User doesn't exist, or failed to communicate with firebase");
        }
    }
    // Get a user class that returns a User object based on the user
    public User getUser(String username, UserFactory userFactory) {
        ApiFuture<DocumentSnapshot> future = userRef.document(username).get();
        try {
            DocumentSnapshot snapshot = future.get();
            return userFactory.createUser(username, (String) snapshot.get("password"));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            throw new RuntimeException("User doesn't exist, or failed to communicate with firebase");
        }
    }
}

package data_access;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.auth.oauth2.GoogleCredentials;
import java.io.FileInputStream;

public class FirebaseInitialize {
    private static boolean initialized = false;

    private FirebaseInitialize(){}

    public static void initFirebase() {
        if (initialized) return; // avoid double initialization
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/java/data_access/firebaseService.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://sudokuthing.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        initialized = true;
    }

    public static FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance();
    }

}

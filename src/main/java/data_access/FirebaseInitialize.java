package data_access;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;

public class FirebaseInitialize {

    private static boolean initialized = false;

    public static void initFirebase() {
        if (initialized) return;

        try {
            InputStream serviceAccount =
                    FirebaseInitialize.class.getClassLoader()
                            .getResourceAsStream("firebaseService.json");

            if (serviceAccount == null) {
                throw new RuntimeException(
                        "firebaseService.json NOT FOUND in src/main/resources/"
                );
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://sudokuthing.firebaseio.com/")
                    .build();

            FirebaseApp.initializeApp(options);
            initialized = true;

            System.out.println("🔥 Firebase initialized successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Firebase init error: " + e.getMessage());
        }
    }

    public static FirebaseDatabase getDatabase() {
        initFirebase();
        return FirebaseDatabase.getInstance();
    }
}

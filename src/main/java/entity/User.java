package entity;

import java.util.UUID;

public class User {
    private final String userId;
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.userId = UUID.randomUUID().toString();
    }

    public String getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}

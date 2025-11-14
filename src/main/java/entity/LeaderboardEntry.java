package entity;

public class LeaderboardEntry {
    private final String userId;
    private final String username;
    private final String difficulty;
    private final long timeMs;

    public LeaderboardEntry(String userId, String username,
                            String difficulty, long timeMs) {
        this.userId = userId;
        this.username = username;
        this.difficulty = difficulty;
        this.timeMs = timeMs;
    }

    public String getUserId() {
        return userId;
    }
    public String getUsername() {
        return username;
    }

    public String getDifficulty() {
        return difficulty;
    }
    public long getTimeMs() {
        return timeMs;
    }
}

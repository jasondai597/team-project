package entity;

// entities/Game.java
public class Game {
    private final String id;           // maybe userId+timestamp
    private final int[][] board;       // current state
    private final int[][] initialBoard;
    private final String difficulty;   // "easy", "medium", ...
    private final String mode;         // "CASUAL" or "RANKED"
    private final long elapsedMs;
    private final boolean finished;

    public Game(String id,
                int[][] board,
                int[][] initialBoard,
                String difficulty,
                String mode,
                long elapsedMs,
                boolean finished) {
        this.id = id;
        this.board = board;
        this.initialBoard = initialBoard;
        this.difficulty = difficulty;
        this.mode = mode;
        this.elapsedMs = elapsedMs;
        this.finished = finished;
    }

    // getters only – keep it immutable for now
}

// entities/LeaderboardEntry.java
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

    // getters...
}


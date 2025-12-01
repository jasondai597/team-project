package entity;

// entities/Game.java
public class Game {
    private final String id;           // maybe userId+timestamp
    private int[][] board;       // current state
    private final int[][] initialBoard;
    private final String difficulty;   // "easy", "medium", ...
    private final String mode;         // "CASUAL" or "RANKED"
    private long elapsedMs;             // snapshot of current elapse time at game end
    private boolean finished;
    private boolean forfeited;          // true if game was forfeited

    public Game(String id,
                int[][] board,
                String difficulty,
                String mode,
                long elapsedMs) {
        this.id = id;
        this.board = board;
        this.initialBoard = board;
        this.difficulty = difficulty;
        this.mode = mode;
        this.elapsedMs = elapsedMs;
        this.finished = false;
        this.forfeited = false;
    }

    // getters only – keep it immutable for now
    public String getId() {
        return id;
    }

    public int[][] getBoard() {
        return board;
    }
    public int[][] getInitialBoard() {
        return initialBoard;
    }
    public String getDifficulty() {
        return difficulty;
    }
    public String getMode() {
        return mode;
    }
    public long getElapsedMs() {
        return elapsedMs;
    }
    public void setElapsedMs(long elapsedMs) {
        this.elapsedMs = elapsedMs;
    }
    public void finish_game(){
        this.finished = true;
    }
    public boolean isFinished() {
        return finished;
    }
    public void setForfeited(boolean forfeited) {
        this.forfeited = forfeited;
    }
    public boolean isForfeited() {
        return forfeited;
    }
}


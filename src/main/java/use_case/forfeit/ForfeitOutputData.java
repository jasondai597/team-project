package use_case.forfeit;

/**
 * Output data for the Forfeit use case.
 */
public class ForfeitOutputData {
    private final String gameId;
    private final String mode;
    private final String difficulty;
    private final long elapsedMs;

    /**
     * Constructs ForfeitOutputData with game statistics.
     * @param gameId
     * @param mode
     * @param difficulty
     * @param elapsedMs
     */
    public ForfeitOutputData(String gameId, String mode, String difficulty, long elapsedMs) {
        this.gameId = gameId;
        this.mode = mode;
        this.difficulty = difficulty;
        this.elapsedMs = elapsedMs;
    }

    public String getGameId() {
        return gameId;
    }

    public String getMode() {
        return mode;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public long getElapsedMs() {
        return elapsedMs;
    }
}

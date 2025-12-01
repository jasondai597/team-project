package use_case.forfeit;

/**
 * Input data for the Forfeit use case.
 */
public class ForfeitInputData {
    private final String gameId;
    private final String difficulty;
    private final long elapsedMs;

    /**
     * Constructs ForfeitInputData with game details.
     * @param gameId
     * @param difficulty
     * @param elapsedMs
     */
    public ForfeitInputData(String gameId, String difficulty, long elapsedMs) {
        this.gameId = gameId;
        this.difficulty = difficulty;
        this.elapsedMs = elapsedMs;
    }

    public String getGameId() {
        return gameId;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public long getElapsedMs() {
        return elapsedMs;
    }
}

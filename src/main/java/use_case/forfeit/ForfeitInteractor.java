package use_case.forfeit;

/**
 * Interactor for the Forfeit use case.
 */
public class ForfeitInteractor implements ForfeitInputBoundary {
    private final ForfeitDataAccessInterface gameDataAccess;
    private final ForfeitOutputBoundary presenter;

    /**
     * Constructs a ForfeitInteractor.
     * @param gameDataAccess
     * @param presenter
     */
    public ForfeitInteractor(ForfeitDataAccessInterface gameDataAccess,
                            ForfeitOutputBoundary presenter) {
        this.gameDataAccess = gameDataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the forfeit use case.
     * @param forfeitInputData
     */
    @Override
    public void execute(ForfeitInputData forfeitInputData) {
        final String gameId = forfeitInputData.getGameId();

        // Mark the game as forfeited and delete it from storage
        if (gameId != null && !gameId.isEmpty() && !"N/A".equals(gameId)) {
            // First, load the game to mark it as forfeited
            gameDataAccess.load(gameId).ifPresent(game -> {
                game.setForfeited(true);
                gameDataAccess.save(game);
            });
            // Then delete the forfeited game from storage
            gameDataAccess.delete(gameId);
        }

        // Prepare output data with game statistics
        final String effectiveGameId = gameId != null ? gameId : "N/A";
        final String effectiveDifficulty = forfeitInputData.getDifficulty() != null
                ? forfeitInputData.getDifficulty() : "Unknown";
        final ForfeitOutputData outputData = new ForfeitOutputData(
            effectiveGameId,
            "Casual",
            effectiveDifficulty,
            forfeitInputData.getElapsedMs()
        );

        // Present the forfeit view
        presenter.presentForfeitView(outputData);
    }
}

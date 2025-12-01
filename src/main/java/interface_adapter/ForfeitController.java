package interface_adapter;

import use_case.forfeit.ForfeitInputBoundary;
import use_case.forfeit.ForfeitInputData;
import use_case.forfeit.ForfeitOutputBoundary;

/**
 * Controller for the Forfeit View.
 */
public class ForfeitController {
    private final ForfeitInputBoundary forfeitInteractor;
    private final ForfeitOutputBoundary forfeitPresenter;
    private final SudokuBoardViewModel sudokuBoardViewModel;

    /**
     * Constructs a ForfeitController.
     * @param forfeitInteractor
     * @param forfeitPresenter
     * @param sudokuBoardViewModel
     */
    public ForfeitController(ForfeitInputBoundary forfeitInteractor,
                            ForfeitOutputBoundary forfeitPresenter,
                            SudokuBoardViewModel sudokuBoardViewModel) {
        this.forfeitInteractor = forfeitInteractor;
        this.forfeitPresenter = forfeitPresenter;
        this.sudokuBoardViewModel = sudokuBoardViewModel;
    }

    /**
     * Navigate to the forfeit view and populate it with game stats.
     */
    public void showForfeit() {
        // Mark the game as forfeited to prevent saving
        sudokuBoardViewModel.setForfeited(true);

        // Get current game information
        final String gameId = sudokuBoardViewModel.getGameId();
        final String difficulty = sudokuBoardViewModel.getDifficulty();
        final long elapsedMs = sudokuBoardViewModel.getElapsedTimeMs();

        // Execute the forfeit use case
        final ForfeitInputData inputData = new ForfeitInputData(gameId, difficulty, elapsedMs);
        forfeitInteractor.execute(inputData);
    }

    /**
     * Navigate back to the main menu and clear game state.
     */
    public void returnToMain() {
        // Clear any incorrect cell highlighting
        sudokuBoardViewModel.clearIncorrectCells();

        // Use presenter to navigate to main menu
        forfeitPresenter.presentMainMenu();
    }
}

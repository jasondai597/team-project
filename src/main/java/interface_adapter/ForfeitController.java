package interface_adapter;

/**
 * Controller for the Forfeit View.
 * Handles user actions from the forfeit screen.
 */
public class ForfeitController {
    private final ViewManagerModel viewManagerModel;
    private final SudokuBoardViewModel sudokuBoardViewModel;

    public ForfeitController(ViewManagerModel viewManagerModel, SudokuBoardViewModel sudokuBoardViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.sudokuBoardViewModel = sudokuBoardViewModel;
    }

    /**
     * Navigate to the forfeit view.
     */
    public void showForfeit() {
        viewManagerModel.setState("forfeit");
        viewManagerModel.firePropertyChange();
    }

    /**
     * Navigate back to the main menu and clear game state.
     */
    public void returnToMain() {
        // Clear any incorrect cell highlighting
        sudokuBoardViewModel.clearIncorrectCells();

        viewManagerModel.setState("main");
        viewManagerModel.firePropertyChange();
    }
}

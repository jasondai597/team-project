package interface_adapter;

import use_case.LoadingSudoku.LoadSudokuInputBoundary;
import use_case.LoadingSudoku.LoadSudokuInputData;

/**
 * Thin controller delegating Sudoku actions to the LoadSudoku use case.
 */
public class SudokuController {

    private final LoadSudokuInputBoundary interactor;

    public SudokuController(LoadSudokuInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadPuzzle(String difficulty) {
        interactor.execute(new LoadSudokuInputData(difficulty));
    }

    public void saveGame(int[][] currentBoard) {
        interactor.saveCurrentGameState(currentBoard);
    }

    public void resumeLastGame() {
        interactor.resumeLastGame();
    }
}

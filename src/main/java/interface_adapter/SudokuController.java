package interface_adapter;

import use_case.LoadingSudoku.LoadSudokuInputBoundary;
import use_case.LoadingSudoku.LoadSudokuInputData;

/**
 * Controller responsible ONLY for loading new Sudoku puzzles.
 */
public class SudokuController {

    private final LoadSudokuInputBoundary interactor;

    public SudokuController(LoadSudokuInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadPuzzle(String difficulty) {
        interactor.execute(new LoadSudokuInputData(difficulty));
    }
}
package use_case.LoadingSudoku;

/**
 * The Input Boundary for loading a sudoku.
 *
 */
public interface LoadSudokuInputBoundary {
    /**
     * The method to execute the loading use case.
     * @param loadSudokuInputData
     *      defines the input that the interactor takes.
     */
    void execute(LoadSudokuInputData loadSudokuInputData);
}
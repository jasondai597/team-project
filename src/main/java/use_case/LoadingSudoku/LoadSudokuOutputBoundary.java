package use_case.LoadingSudoku;

import entity.SudokuPuzzle;

public interface LoadSudokuOutputBoundary {
    /**
     * Presents the board to the view model.
     * @param puzzle
     *      The entity object that the view model maintains.
     * @param gameId
     *      The id used to store the game if the user wanted to save it.
     */
    void present(SudokuPuzzle puzzle, String gameId);

    /**
     * Tells the view model that something wrong has happened.
     * @param message
     *      The error message the user gets if something wrong happened during loading process.
     */
    void presentError(String message);

    /**
     * Used to present a board that's been saved before.
     * @param board
     *      a board that is presented to the view model.
     */
    void presentLoadedBoard(int[][] board);
}

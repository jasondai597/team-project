package use_case.LoadingSudoku;

import entity.SudokuPuzzle;

public interface LoadSudokuOutputBoundary {
    void present(SudokuPuzzle puzzle, String gameId);
    void presentError(String message);
    void presentLoadedBoard(int[][] board);
}

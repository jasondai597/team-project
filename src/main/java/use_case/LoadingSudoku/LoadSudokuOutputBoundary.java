package use_case.LoadingSudoku;


import entity.SudokuPuzzle;

public interface LoadSudokuOutputBoundary {
    void present(SudokuPuzzle puzzle);

    void presentError(String s);
}

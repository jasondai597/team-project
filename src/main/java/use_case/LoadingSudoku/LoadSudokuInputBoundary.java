package use_case.LoadingSudoku;

import use_case.hints.hintInputData;

public interface LoadSudokuInputBoundary {
    void execute(LoadSudokuInputData request);

    void saveCurrentGameState(int[][] currentBoard);

    void resumeLastGame();
}


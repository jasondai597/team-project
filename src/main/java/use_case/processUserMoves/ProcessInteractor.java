package use_case.processUserMoves;

import entity.SudokuPuzzle;
import interface_adapter.SudokuBoardViewModel;

public class ProcessInteractor implements ProcessInputBoundary{
    private final ProcessOutputBoundary presenter;
    private final SudokuPuzzle puzzle;


    public ProcessInteractor(SudokuPuzzle puzzle, ProcessOutputBoundary presenter) {
        this.presenter = presenter;
        this.puzzle = puzzle;
    }
    @Override
    public void execute(int row, int col, int value) {
        int[][] board = puzzle.getInitial();
        board[row][col] = value;
        presenter.presentSuccess(row, col, value);
    }
}

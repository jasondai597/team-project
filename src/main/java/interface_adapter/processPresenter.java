package interface_adapter;

import entity.SudokuPuzzle;
import use_case.processUserMoves.ProcessOutputBoundary;

public class processPresenter implements ProcessOutputBoundary {
    private final SudokuBoardViewModel viewModel;

    public processPresenter(SudokuBoardViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void presentSuccess(int row, int col, int value) {
        int[][] board = viewModel.getBoard();
        int[][] newBoard = new int[9][9];
        for (int r = 0; r < 9; r++) {
            newBoard[r] = board[r].clone();
        }
        newBoard[row][col] = value;
        viewModel.setBoard(newBoard);
    }

    @Override
    public void presentInvalidMove(String message) {
        viewModel.setErrorMessage(message);
    }
}

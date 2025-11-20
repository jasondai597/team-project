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
        int[][] newBoard = board.clone();
        newBoard[row][col] = value;
        viewModel.setBoard(newBoard);

    }

    @Override
    public void presentInvalidMove(String message) {
        viewModel.setErrorMessage(message);
    }
}

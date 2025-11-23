package interface_adapter;

import use_case.hints.HintOutputBoundary;

public class HintPresenter implements HintOutputBoundary {
    private final SudokuBoardViewModel viewModel;

    public HintPresenter(SudokuBoardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentHint(int r, int c, int value) {
        int[][] board = viewModel.getBoard();
        int[][] newBoard = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, 9);
        }
        newBoard[r][c] = value;
        viewModel.setBoard(newBoard);


    }

    @Override
    public void presentNoHint() {
        viewModel.setErrorMessage("No available hints.");
    }


}

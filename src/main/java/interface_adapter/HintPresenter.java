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
        board[r][c] = value;
        viewModel.setBoard(board);
        viewModel.firePropertyChange("board", null, board);

    }

    @Override
    public void presentNoHint() {
        viewModel.setErrorMessage("No available hints.");
    }


}

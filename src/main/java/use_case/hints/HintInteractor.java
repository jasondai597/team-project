package use_case.hints;

import interface_adapter.SudokuBoardViewModel;

public class HintInteractor implements HintInputBoundary{
    private final SudokuBoardViewModel viewModel;
    private final HintOutputBoundary presenter;

    public HintInteractor(SudokuBoardViewModel viewModel,
                          HintOutputBoundary presenter) {
        this.viewModel = viewModel;
        this.presenter = presenter;
    }

    public void execute() {

        int[][] board = viewModel.getBoard();
        int[][] solution = viewModel.getSolution();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] != solution[r][c]) {

                    int correctVal = solution[r][c];

                    presenter.presentHint(r, c, correctVal);
                    return;
                }
            }
        }

        presenter.presentNoHint();
    }




}

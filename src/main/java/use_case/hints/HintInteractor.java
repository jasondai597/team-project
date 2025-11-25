package use_case.hints;

public class HintInteractor implements HintInputBoundary {

    private final HintOutputBoundary presenter;

    public HintInteractor(HintOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void execute(hintInputData inputData) {
        int[][] sudokuBoard = inputData.getBoard();
        int[][] sudokuSolution = inputData.getSolution();

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (sudokuBoard[r][c] != sudokuSolution[r][c]) {
                    int correctVal = sudokuSolution[r][c];
                    presenter.presentHint(r, c, correctVal);
                    return;
                }
            }
        }

        presenter.presentNoHint();
    }
}

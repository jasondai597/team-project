package use_case.Check;

public class CheckInteractor implements checkInputBoundary{
    private final CheckOutputBoundary presenter;
    public CheckInteractor(CheckOutputBoundary presenter) {
        this.presenter = presenter;
    }
    public void check(CheckInputData checkInputData) {
        int[][] player_board = checkInputData.getPlayer_board();
        int[][] solution = checkInputData.getSolution();
        boolean[][] incorrectCells = new boolean[9][9];
        boolean allCorrect = true;
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                incorrectCells[r][c] = (player_board[r][c] != solution[r][c]);
                if (player_board[r][c] != solution[r][c]) {
                    allCorrect = false;
                }
            }
        }
        if (allCorrect) {
            presenter.presentWin();
        }
        else{
        presenter.presentAnswer(incorrectCells);}

    }

}

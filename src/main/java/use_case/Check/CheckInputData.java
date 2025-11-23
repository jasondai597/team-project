package use_case.Check;

public class CheckInputData {
    private int[][] player_board;
    private int[][] solution;
    public CheckInputData(int[][] player_board, int[][] solution) {
        this.player_board = player_board;
        this.solution = solution;
    }
    public int[][] getPlayer_board() {
        return player_board;
    }
    public int[][] getSolution() {
        return solution;
    }
}

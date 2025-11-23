package use_case.hints;

public class hintInputData {
    private int[][]  board;
    private int[][] solution;
    public hintInputData(int[][] board, int[][] solution) {
        this.board = board;
        this.solution = solution;
    }
    public int[][] getBoard() {
        return board;
    }
    public int[][] getSolution() {
        return solution;
    }
}

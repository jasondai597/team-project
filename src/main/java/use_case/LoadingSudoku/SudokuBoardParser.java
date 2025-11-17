package use_case.LoadingSudoku;

public class SudokuBoardParser {
    public static int[][] parse(String puzzleString) {
        int[][] board = new int[9][9];
        for (int i = 0; i < 81; i++) {
            char c = puzzleString.charAt(i);
            board[i / 9][i % 9] = c == '0' ? 0 : Character.getNumericValue(c);
        }
        return board;
    }

}

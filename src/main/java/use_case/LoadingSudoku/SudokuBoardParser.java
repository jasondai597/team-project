package use_case.LoadingSudoku;

public class SudokuBoardParser {
    private static final int HEIGHT = 9;
    private static final int WIDTH = 9;
    private static final int BOARD_SIZE = 81;

    /**
     * Parses the string into an actual board.
     * @param puzzleString
     *      The string that the API gave us.
     * @return
     *      the board as a two-dimensional array.
     */
    public static int[][] parse(String puzzleString) {
        final int[][] board = new int[HEIGHT][WIDTH];
        for (int i = 0; i < BOARD_SIZE; i++) {
            cellValue(puzzleString, i, board);

        }
        return board;
    }

    private static void cellValue(String puzzleString, int position, int[][] board) {
        final char c = puzzleString.charAt(position);
        if (c == '0') {
            board[position / HEIGHT][position % WIDTH] = 0;
        }
        else {
            board[position / HEIGHT][position % WIDTH] = Character.getNumericValue(c);
        }

    }

}

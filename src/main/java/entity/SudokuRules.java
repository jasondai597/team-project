package entity;

import java.util.Arrays;

public final class SudokuRules {
    private SudokuRules() {}

    public static boolean inBounds(int r, int c) {
        return r >= 0 && r < 9 && c >= 0 && c < 9;
    }

    public static boolean isWritable(int[][] initial, int r, int c) {
        return inBounds(r, c) && initial[r][c] == 0;
    }

    public static boolean isValidMove(int[][] board, int r, int c, int v) {
        if (!inBounds(r,c) || v < 1 || v > 9) return false;
        for (int j = 0; j < 9; j++) if (board[r][j] == v) return false; // row
        for (int i = 0; i < 9; i++) if (board[i][c] == v) return false; // col
        int rs = (r/3)*3, cs = (c/3)*3;
        for (int i = rs; i < rs+3; i++)
            for (int j = cs; j < cs+3; j++)
                if (board[i][j] == v) return false;                     // box
        return true;
    }

    public static boolean isSolved(int[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = board[r][c];
                if (v == 0) return false;
                // check uniqueness by temporarily clearing self
                int tmp = board[r][c];
                board[r][c] = 0;
                boolean ok = isValidMove(board, r, c, tmp);
                board[r][c] = tmp;
                if (!ok) return false;
            }
        }
        return true;
    }

    public static int[][] copy(int[][] a) {
        int[][] b = new int[a.length][];
        for (int i = 0; i < a.length; i++) b[i] = Arrays.copyOf(a[i], a[i].length);
        return b;
    }

    public static void validate9x9(int[][] g, String name) {
        if (g == null || g.length != 9) throw new IllegalArgumentException(name+" must be 9x9");
        for (int i = 0; i < 9; i++) {
            if (g[i] == null || g[i].length != 9) throw new IllegalArgumentException(name+" must be 9x9");
            for (int j = 0; j < 9; j++) {
                int v = g[i][j];
                if (v < 0 || v > 9) throw new IllegalArgumentException(name+"["+i+"]["+j+"] not in 0..9");
            }
        }
    }
}

package use_case.processUserMoves;

import entity.SudokuPuzzle;

/**
 * Interactor for processing a user's move on the Sudoku board.
 */
public class ProcessInteractor implements ProcessInputBoundary {

    private final ProcessOutputBoundary presenter;

    // Must NOT be final — updated after puzzle loads
    private SudokuPuzzle puzzle;

    public ProcessInteractor(SudokuPuzzle puzzle, ProcessOutputBoundary presenter) {
        this.puzzle = puzzle;
        this.presenter = presenter;
    }

    /**
     * Allows updating the current puzzle when a new board is loaded.
     */
    public void setPuzzle(SudokuPuzzle puzzle) {
        this.puzzle = puzzle;
    }

    /**
     * Apply the move and notify presenter.
     */
    @Override
    public void execute(int row, int col, int value) {
        if (puzzle == null) {
            System.out.println("ProcessInteractor: No active puzzle yet.");
            return;
        }

        int[][] board = puzzle.getInitial();

        // Bounds safety
        if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
            System.out.println("ProcessInteractor: Move out of bounds.");
            return;
        }

        board[row][col] = value;

        presenter.presentSuccess(row, col, value);
    }
}

package interface_adapter;

import entity.SudokuPuzzle;
import use_case.LoadingSudoku.LoadSudokuOutputBoundary;

public class SudokuPresenter implements LoadSudokuOutputBoundary {

    private final SudokuBoardViewModel viewModel;

    public SudokuPresenter(SudokuBoardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SudokuPuzzle puzzle, String gameId) {
        viewModel.setGameId(gameId); // Update the ID
        viewModel.setInitialBoard(puzzle.getInitial());
        viewModel.setBoard(puzzle.getInitial());
        viewModel.setSolution(puzzle.getSolution());
        viewModel.clearIncorrectCells();
    }

    @Override
    public void presentLoadedBoard(int[][] board) {
        viewModel.setBoard(board);
        viewModel.firePropertyChange("board", null, board);
    }

    @Override
    public void presentError(String message) {
        viewModel.setErrorMessage(message);
    }
}

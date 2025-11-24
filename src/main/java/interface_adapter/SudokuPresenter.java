package interface_adapter;

import entity.SudokuPuzzle;
import use_case.LoadingSudoku.LoadSudokuOutputBoundary;

public class SudokuPresenter implements LoadSudokuOutputBoundary {
    private final SudokuBoardViewModel viewModel;
    public SudokuPresenter(SudokuBoardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SudokuPuzzle puzzle) {
        viewModel.setInitialBoard(puzzle.getStartingBoard());
       viewModel.setBoard(puzzle.getInitial());
       viewModel.setSolution(puzzle.getSolution());

    }
    @Override
    public void presentError(String message) {
        viewModel.setErrorMessage(message);
    }

    @Override
    public void presentLoadedBoard(int[][] board) {
        viewModel.setBoard(board);
        viewModel.firePropertyChange("board", null, board);
    }

}

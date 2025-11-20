package interface_adapter;

import entity.SudokuPuzzle;
import use_case.LoadingSudoku.LoadSudokuOutputBoundary;
import interface_adapter.SudokuBoardViewModel;

public class SudokuPresenter implements LoadSudokuOutputBoundary {
    private final SudokuBoardViewModel viewModel;

    public SudokuPresenter(SudokuBoardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SudokuPuzzle puzzle) {
       viewModel.setBoard(puzzle.getInitial());
       viewModel.setSolution(puzzle.getSolution());
    }
    @Override
    public void presentError(String message) {
        viewModel.setErrorMessage(message);
    }


}

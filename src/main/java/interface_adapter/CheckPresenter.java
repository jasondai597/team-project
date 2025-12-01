package interface_adapter;

import use_case.Check.CheckOutputBoundary;

import javax.swing.*;

public class CheckPresenter implements CheckOutputBoundary {
    private final SudokuBoardViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    public CheckPresenter(SudokuBoardViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }
    @Override
    public void presentAnswer(boolean[][] incorrectCells) {
        viewModel.setIncorrectCells(incorrectCells);

    }
    public void presentWin(){
        viewManagerModel.setState("Win");
        viewManagerModel.firePropertyChange();

    }
}

package interface_adapter;

import use_case.Check.CheckOutputBoundary;

import javax.swing.*;

public class CheckPresenter implements CheckOutputBoundary {
    private final SudokuBoardViewModel viewModel;

    public CheckPresenter(SudokuBoardViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void presentAnswer(boolean[][] incorrectCells) {
        viewModel.setIncorrectCells(incorrectCells);

    }
    public void presentWin(){
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(null, "Congratulations! You solved the puzzle!", "You Win!", JOptionPane.INFORMATION_MESSAGE)
        );

    }
}

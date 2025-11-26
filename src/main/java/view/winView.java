package view;

import interface_adapter.SudokuBoardViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;

public class winView extends JPanel {

    private final ViewManagerModel viewManagerModel;
    private final SudokuBoardViewModel SudokuBoardViewModel;
    private final String viewName = "Win";
    public winView(ViewManagerModel viewManagerModel,  SudokuBoardViewModel sudokuBoardView) {
        this.viewManagerModel = viewManagerModel;
        this.SudokuBoardViewModel = sudokuBoardView;
        setupUI();
    }

    private void setupUI() {
        this.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("You Win!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36));

        JLabel messageLabel = new JLabel("Congratulations on solving the puzzle!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("SansSerif", Font.PLAIN, 20));

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1, 10, 10));
        centerPanel.add(titleLabel);
        centerPanel.add(messageLabel);

        JButton menuButton = new JButton("Back to Menu");
        menuButton.addActionListener(e -> {
            this.SudokuBoardViewModel.clearIncorrectCells();
            viewManagerModel.setState("main");
            viewManagerModel.firePropertyChange();
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(menuButton);

        // Add everything
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
    public String getViewName() {
        return viewName;
    }
}

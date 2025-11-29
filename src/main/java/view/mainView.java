package view;

import interface_adapter.ResumeGameController;
import interface_adapter.SudokuBoardViewModel;
import interface_adapter.SudokuController;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class mainView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "main";
    private final ViewManagerModel viewManagerModel;
    private final SudokuController sudokuController;
    private final ResumeGameController resumeController; // NEW
    private final SudokuBoardViewModel viewModel;

    private JButton playButton;
    private JButton rankedButton;
    private JButton resumeButton;
    private JButton loginButton;

    public mainView(ViewManagerModel viewManagerModel,
                    SudokuController sudokuController,
                    ResumeGameController resumeController, // NEW
                    SudokuBoardViewModel viewModel) {

        this.viewManagerModel = viewManagerModel;
        this.sudokuController = sudokuController;
        this.resumeController = resumeController;
        this.viewModel = viewModel;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("SudokuThing", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 0, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        centerPanel.setBackground(Color.WHITE);

        // Play New Game
        playButton = new JButton("Play");
        playButton.setActionCommand("PLAY");
        playButton.addActionListener(this);

        // Resume Game
        resumeButton = new JButton("Resume Last Game");
        resumeButton.setActionCommand("RESUME");
        resumeButton.addActionListener(this);

        // Ranked (disabled for now)
        rankedButton = new JButton("Ranked");
        rankedButton.setActionCommand("RANKED");
        rankedButton.addActionListener(this);
        rankedButton.setOpaque(true);
        rankedButton.setBackground(new Color(196, 196, 196));
        rankedButton.setForeground(new Color(150, 150, 150));
        rankedButton.setFocusPainted(false);

        centerPanel.add(playButton);
        centerPanel.add(resumeButton);
        centerPanel.add(rankedButton);

        add(centerPanel, BorderLayout.CENTER);

        // Login
        loginButton = new JButton("Login");
        loginButton.setActionCommand("LOGIN");
        loginButton.addActionListener(this);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loginButton);
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();

        switch (cmd) {
            case "PLAY":
                viewManagerModel.setState("difficulty");
                viewManagerModel.firePropertyChange();
                break;

            case "RESUME":
                // 1. Clear old highlights
                viewModel.clearIncorrectCells();
                // 2. Call the dedicated Resume Controller
                resumeController.resume();
                // 3. Navigate (Note: ideally the Presenter handles the state switch on success,
                //    but doing it here ensures the view switches if the data loads fast)
                viewManagerModel.setState("unranked");
                viewManagerModel.firePropertyChange();
                break;

            case "RANKED":
                // Note: Ranked uses the standard controller for now as it's under construction
                sudokuController.loadPuzzle("easy");
                viewManagerModel.setState("ranked");
                viewManagerModel.firePropertyChange();
                break;

            case "LOGIN":
                System.out.println("Login clicked");
                break;
        }
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // nothing for now
    }
}
package view;

import interface_adapter.ResumeGameController;
import interface_adapter.SudokuBoardViewModel;
import interface_adapter.SudokuController;
import interface_adapter.ViewManagerModel;
import interface_adapter.loggedIn.LoggedInState;
import interface_adapter.loggedIn.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class loggedInView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "loggedIn";
    private final ViewManagerModel viewManagerModel;
    private final SudokuController sudokuController;
    private final ResumeGameController resumeController; // NEW
    private final SudokuBoardViewModel viewModel;
    private final LoggedInViewModel loggedInViewModel;

    private JButton playButton;
    private JButton rankedButton;
    private JButton resumeButton;

    private JLabel welcome_label;

    public loggedInView(ViewManagerModel viewManagerModel,
                    SudokuController sudokuController,
                    ResumeGameController resumeController, // NEW
                    SudokuBoardViewModel viewModel,
                    LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.sudokuController = sudokuController;
        this.resumeController = resumeController;
        this.viewModel = viewModel;
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

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

        centerPanel.add(playButton);
        centerPanel.add(resumeButton);
        centerPanel.add(rankedButton);

        add(centerPanel, BorderLayout.CENTER);

        // Welcome User
        welcome_label = new JLabel();
        welcome_label.setFont(new Font("Arial", Font.PLAIN, 20));

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(welcome_label);
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

            default:
                System.out.println("Not implemented yet");

        }
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            // Update welcome to show actual username
            final LoggedInState state = (LoggedInState) evt.getNewValue();
            welcome_label.setText("Welcome, " + state.getUsername() + "!");
        }
    }
}

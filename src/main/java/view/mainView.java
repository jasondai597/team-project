package view;

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

    private JButton playButton;
    private JButton rankedButton;
    private JButton loginButton;

    public mainView(ViewManagerModel viewManagerModel, SudokuController sudokuController) {
        this.viewManagerModel = viewManagerModel;
        this.sudokuController = sudokuController;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("SudokuThing", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1, 0, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        centerPanel.setBackground(Color.WHITE);

        playButton = new JButton("Play");
        playButton.setActionCommand("PLAY");
        playButton.addActionListener(this);

        rankedButton = new JButton("Ranked");
        rankedButton.setActionCommand("RANKED");
        rankedButton.addActionListener(this);

        rankedButton.setOpaque(true);
        rankedButton.setBackground(new Color(196,196,196));
        rankedButton.setForeground(new Color(150,150,150));
        rankedButton.setFocusPainted(false);

        centerPanel.add(playButton);
        centerPanel.add(rankedButton);

        add(centerPanel, BorderLayout.CENTER);

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
                // Load a new puzzle for unranked mode
                sudokuController.loadPuzzle("easy");
                viewManagerModel.setState("unranked");
                viewManagerModel.firePropertyChange();
                break;

            case "RANKED":
                // Load a new puzzle for ranked mode
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
    }

}

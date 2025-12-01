package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import interface_adapter.ForfeitController;
import interface_adapter.ForfeitState;
import interface_adapter.ForfeitViewModel;

public class ForfeitView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "forfeit";
    private final ForfeitViewModel forfeitViewModel;
    private final ForfeitController forfeitController;

    private final JButton returnButton = new JButton("Return to Main Menu");
    private final JLabel difficultyLabel = new JLabel("");
    private final JLabel timeLabel = new JLabel("");

    public ForfeitView(ForfeitViewModel forfeitViewModel, ForfeitController forfeitController) {
        this.forfeitViewModel = forfeitViewModel;
        this.forfeitController = forfeitController;
        this.forfeitViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(20, 20));
        setBackground(Color.WHITE);

        // Create main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Title - "Game Forfeited"
        JLabel titleLabel = new JLabel("Game Forfeited");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(200, 50, 50)); // Red color for forfeit
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Subtitle message
        JLabel messageLabel = new JLabel("Don't give up! Try again.");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        messageLabel.setForeground(Color.DARK_GRAY);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(messageLabel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Stats panel with better formatting
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(new Color(245, 245, 245));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));

        JLabel statsTitle = new JLabel("Game Statistics");
        statsTitle.setFont(new Font("Arial", Font.BOLD, 18));
        statsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsPanel.add(statsTitle);

        statsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        // Difficulty
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsPanel.add(difficultyLabel);

        statsPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Time played
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statsPanel.add(timeLabel);

        contentPanel.add(statsPanel);

        contentPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Return button
        returnButton.setFont(new Font("Arial", Font.BOLD, 18));
        returnButton.setPreferredSize(new Dimension(250, 50));
        returnButton.setMaximumSize(new Dimension(250, 50));
        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        returnButton.setBackground(new Color(70, 130, 180));
        returnButton.setForeground(Color.BLACK);
        returnButton.setOpaque(true);
        returnButton.setBorderPainted(true);
        returnButton.setFocusPainted(false);
        returnButton.addActionListener(this);
        contentPanel.add(returnButton);

        add(contentPanel, BorderLayout.CENTER);

        updateView();
    }

    private void updateView() {
        ForfeitState state = forfeitViewModel.getState();

        // Format difficulty with capitalization
        String difficulty = state.getDifficulty();
        if (difficulty != null && !difficulty.isEmpty()) {
            difficulty = difficulty.substring(0, 1).toUpperCase() + difficulty.substring(1).toLowerCase();
        }
        difficultyLabel.setText("Difficulty: " + difficulty);

        // Format time in a readable way
        long seconds = state.getElapsedMs() / 1000;
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;

        String timeText;
        if (minutes > 0) {
            timeText = String.format("Time Played: %d min %d sec", minutes, remainingSeconds);
        } else {
            timeText = String.format("Time Played: %d seconds", remainingSeconds);
        }
        timeLabel.setText(timeText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        forfeitController.returnToMain();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("state".equals(evt.getPropertyName())) {
            updateView();
        }
    }

    public String getViewName() {
        return viewName;
    }
}

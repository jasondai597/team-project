package view;

import interface_adapter.ForfeitController;
import interface_adapter.ForfeitState;
import interface_adapter.ForfeitViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ForfeitView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "forfeit";
    private final ForfeitViewModel forfeitViewModel;
    private final ForfeitController forfeitController;

    private final JButton okButton = new JButton("OK");
    private final JLabel gameIdLabel = new JLabel("");
    private final JLabel modeLabel = new JLabel("");
    private final JLabel difficultyLabel = new JLabel("");
    private final JLabel elapsedLabel = new JLabel("");
    private final JLabel finishedLabel = new JLabel("");

    public ForfeitView(ForfeitViewModel forfeitViewModel, ForfeitController forfeitController) {
        this.forfeitViewModel = forfeitViewModel;
        this.forfeitController = forfeitController;
        this.forfeitViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(20, 20));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Game ID: "));
        row1.add(gameIdLabel);
        infoPanel.add(row1);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Mode: "));
        row2.add(modeLabel);
        infoPanel.add(row2);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(new JLabel("Difficulty: "));
        row3.add(difficultyLabel);
        infoPanel.add(row3);

        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row4.add(new JLabel("Elapsed: "));
        row4.add(elapsedLabel);
        infoPanel.add(row4);

        JPanel row5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row5.add(new JLabel("Finished: "));
        row5.add(finishedLabel);
        infoPanel.add(row5);

        add(infoPanel, BorderLayout.CENTER);

        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(this);
        add(okButton, BorderLayout.SOUTH);

        updateView();
    }

    private void updateView() {
        ForfeitState state = forfeitViewModel.getState();
        gameIdLabel.setText(state.getGameId());
        modeLabel.setText(state.getMode());
        difficultyLabel.setText(state.getDifficulty());
        elapsedLabel.setText((state.getElapsedMs() / 1000) + "s");
        finishedLabel.setText(String.valueOf(state.isFinished()));
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

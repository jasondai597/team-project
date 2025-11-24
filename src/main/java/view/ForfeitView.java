package view;

import entity.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ForfeitView extends JPanel implements ActionListener {

    private final JButton okButton = new JButton("OK");
    private final JFrame frame;
    private CardLayout cardLayout;
    private JPanel cardContainer;

    public ForfeitView(JFrame frame, Game gameInfo) {
        this(frame, gameInfo, null, null);
    }

    public ForfeitView(JFrame frame, Game gameInfo, CardLayout cardLayout, JPanel cardContainer) {
        this.frame = frame;
        this.cardLayout = cardLayout;
        this.cardContainer = cardContainer;
        setLayout(new BorderLayout(20, 20));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(new JLabel("Game ID: "));
        row1.add(new JLabel(gameInfo.getId()));
        infoPanel.add(row1);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Mode: "));
        row2.add(new JLabel(gameInfo.getMode()));
        infoPanel.add(row2);

        JPanel row3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row3.add(new JLabel("Difficulty: "));
        row3.add(new JLabel(gameInfo.getDifficulty()));
        infoPanel.add(row3);

        JPanel row4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row4.add(new JLabel("Elapsed: "));
        row4.add(new JLabel((gameInfo.getElapsedMs() / 1000) + "s"));
        infoPanel.add(row4);

        JPanel row5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row5.add(new JLabel("Finished: "));
        row5.add(new JLabel(String.valueOf(gameInfo.isFinished())));
        infoPanel.add(row5);

        add(infoPanel, BorderLayout.CENTER);

        okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        okButton.addActionListener(this);
        add(okButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (cardLayout != null && cardContainer != null) {
            cardLayout.show(cardContainer, "main");
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Forfeit View Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLocationRelativeTo(null);


            int[][] dummyBoard = new int[9][9];
            Game testGame = new Game(
                    "GAME123",
                    dummyBoard,
                    "EASY",
                    "CASUAL",
                    12345
            );

            ForfeitView forfeitView = new ForfeitView(frame, testGame);

            frame.getContentPane().add(forfeitView);
            frame.setVisible(true);
        });
    }


}

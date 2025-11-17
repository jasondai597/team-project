package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class difficultyView extends JPanel implements ActionListener, PropertyChangeListener {

    private JButton easyButton;
    private JButton mediumButton;
    private JButton hardButton;

    public difficultyView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("SudokuThing", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(3, 1, 0, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 100, 30, 100));
        centerPanel.setBackground(Color.WHITE);

        easyButton = new JButton("Easy");
        easyButton.setFont(new Font("Arial", Font.PLAIN, 20));
        easyButton.setActionCommand("EASY");
        easyButton.addActionListener(this);

        mediumButton = new JButton("Medium");
        mediumButton.setFont(new Font("Arial", Font.PLAIN, 20));
        mediumButton.setActionCommand("MEDIUM");
        mediumButton.addActionListener(this);

        hardButton = new JButton("Hard");
        hardButton.setFont(new Font("Arial", Font.PLAIN, 20));
        hardButton.setActionCommand("HARD");
        hardButton.addActionListener(this);

        centerPanel.add(easyButton);
        centerPanel.add(mediumButton);
        centerPanel.add(hardButton);
        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();

        switch (cmd) {
            case "EASY":
                System.out.println("easy clicked");
                break;
            case "MEDIUM":
                System.out.println("medium clicked");
                break;
            case "HARD":
                System.out.println("hard clicked");
                break;

        }

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    //Greyed out for testing purposes
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sudoku App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setContentPane(new difficultyView());
            frame.pack();
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });

    }
}

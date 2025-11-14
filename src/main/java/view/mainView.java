package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class mainView extends JPanel implements ActionListener, PropertyChangeListener {


    private JButton playButton;
    private JButton rankedButton;
    private JButton loginButton;

    public mainView() {
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
                System.out.println("Play clicked");
                break;

            case "RANKED":
                JOptionPane.showMessageDialog(
                        this,
                        "You must login first.",
                        "Ranked Mode Locked",
                        JOptionPane.WARNING_MESSAGE
                );
                break;

            case "LOGIN":
                System.out.println("Login clicked");
                break;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    // Greyed out for testing purposes
//    public static void main(String[] args) {
//
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Sudoku App");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            frame.setContentPane(new mainView());
//            frame.pack();
//            frame.setSize(400, 400);
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//
//    }
}

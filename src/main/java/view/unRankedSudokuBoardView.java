package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
//Comment added to make an extra commit with notes, 67
public class unRankedSudokuBoardView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JTextField[][] cells = new JTextField[9][9];

    public unRankedSudokuBoardView() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("SUDOKU-THING", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(9, 9));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);

                int top = (r % 3 == 0) ? 3 : 1;
                int left = (c % 3 == 0) ? 3 : 1;
                int bottom = (r == 8) ? 3 : 1;
                int right = (c == 8) ? 3 : 1;
                tf.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                cells[r][c] = tf;
                grid.add(tf);

            }
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton Hint = new JButton("HINT");
        Hint.setPreferredSize(new Dimension(200, 100));
        Hint.addActionListener(this);

        JButton Check = new JButton("CHECK");
        Check.setPreferredSize(new Dimension(200, 100));
        Check.addActionListener(this);

        JButton Forfeit = new JButton("FORFEIT");
        Forfeit.setPreferredSize(new Dimension(200, 100));
        Forfeit.addActionListener(this);

        buttonPanel.add(Hint);
        buttonPanel.add(Check);
        buttonPanel.add(Forfeit);

        add(grid, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();

        switch (event) {
            case "HINT":
                JOptionPane.showMessageDialog(this, "HINT");
                break;
            case "CHECK":
                JOptionPane.showMessageDialog(this, "CHECK");
                break;
            case "FORFEIT":
                int result = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to forfeit?",
                        "Confirm Forfeit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (result == JOptionPane.YES_OPTION) {
                    //This is temporary until we add the controllers to switch to mainView
                    JOptionPane.showMessageDialog(this, "You forfeited the game!");
                }

        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Unranked Sudoku Board Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 900);
            frame.setLayout(new BorderLayout());

            unRankedSudokuBoardView boardView = new unRankedSudokuBoardView();

            frame.add(boardView, BorderLayout.CENTER);

            frame.setVisible(true);

        });

    }

}

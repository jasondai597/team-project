package view;

import interface_adapter.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
//Comment added to make an extra commit with notes, 67
public class unRankedSudokuBoardView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JTextField[][] cells = new JTextField[9][9];
    private final SudokuBoardViewModel viewModel;
    private final SudokuController controller;
    private final hintController hint;
    private final processController process;
    private final CheckController check;
    private final ForfeitController forfeitController;

    public unRankedSudokuBoardView(SudokuBoardViewModel viewModel, SudokuController controller,
            hintController hintController, processController process, CheckController check,
            ForfeitController forfeitController) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.hint = hintController;
        this.process = process;
        this.check = check;
        this.forfeitController = forfeitController;
        setLayout(new BorderLayout());
        viewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("SUDOKU-THING", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(9, 9));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setOpaque(true);
                tf.setBackground(Color.WHITE);
                tf.setFont(new Font("SansSerif", Font.BOLD, 30));
                final int finalR = r;
                final int finalC = c;

                // Add document filter to restrict input to single digit 1-9
                ((javax.swing.text.AbstractDocument) tf.getDocument()).setDocumentFilter(new javax.swing.text.DocumentFilter() {
                    @Override
                    public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
                            throws javax.swing.text.BadLocationException {
                        if (text == null || text.isEmpty()) {
                            super.replace(fb, offset, length, text, attrs);
                            return;
                        }
                        // Only allow single digit 1-9
                        if (text.matches("[1-9]") && fb.getDocument().getLength() - length < 1) {
                            super.replace(fb, offset, length, text, attrs);
                        }
                    }
                });

                tf.addActionListener(e -> {
                    try {
                        String text = tf.getText();
                        int value = text.isEmpty() ? 0 : Integer.parseInt(text);
                        process.processMove(finalR, finalC, value);
                    } catch (NumberFormatException ex) {
                        tf.setText("");
                    }
                });

                int top = (r % 3 == 0) ? 3 : 1;
                int left = (c % 3 == 0) ? 3 : 1;
                int bottom = (r == 8) ? 3 : 1;
                int right = (c == 8) ? 3 : 1;
                tf.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                cells[r][c] = tf;
                grid.add(tf);

            }
        }

        // if a puzzle was already loaded into the viewModel before this view
        // was constructed, initialize the UI from the model now.
        int[][] current = viewModel.getBoard();
        if (current != null) {
            updateBoard(current);
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
                hint.hint(viewModel.getBoard(), viewModel.getSolution());
                break;
            case "CHECK":
                check.check(viewModel.getBoard(), viewModel.getSolution());

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

                    int[][] currentBoard = viewModel.getBoard();

                    controller.saveGame(currentBoard);

                    System.out.println("Saved game on quit!");

                    JOptionPane.showMessageDialog(this, "You forfeited the game! Progress saved.");
                    forfeitController.showForfeit();
                }
                break;


        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("board".equals(evt.getPropertyName())) {

            int[][] board = (int[][]) evt.getNewValue();
            int[][] initial = viewModel.getInitialBoard();
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {

                    JTextField tf = cells[r][c];
                    int val = board[r][c];

                    tf.setText(val == 0 ? "" : String.valueOf(val));

                    if (initial[r][c] != 0) {
                        tf.setEditable(false);
                        tf.setBackground(new Color(230,230,230));
                    } else {
                        tf.setEditable(true);
                        tf.setBackground(Color.WHITE);
                    }
                }
            }
        }
        else if ("incorrect".equals(evt.getPropertyName())) {
            boolean[][] incorrect = (boolean[][]) evt.getNewValue();
            int[][] initial = viewModel.getInitialBoard();

            for(int r = 0; r < 9; r++){
                for(int c = 0; c < 9; c++){
                    JTextField tf = cells[r][c];
                    if(incorrect[r][c]){
                        tf.setBackground(Color.RED);
                    }
                    else{
                        tf.setBackground(Color.GREEN);
                    }
                }
            }

        }
        else if ("error".equals(evt.getPropertyName())) {
            String message = (String) evt.getNewValue();
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }


    }

    private void updateBoard(int[][] board) {
        int[][] initial = viewModel.getInitialBoard();
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                JTextField tf = cells[r][c];
                int val = board[r][c];
                tf.setText(val == 0 ? "" : String.valueOf(val));

                if (initial != null && initial[r][c] != 0) {
                    tf.setEditable(false);
                    tf.setBackground(new Color(230,230,230));
                } else {
                    tf.setEditable(true);
                    tf.setBackground(Color.WHITE);
                }
            }
        }
    }
}

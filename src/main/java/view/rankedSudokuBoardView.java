package view;

import interface_adapter.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

// Ranked board view wired to existing use-case / adapter modules
public class rankedSudokuBoardView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JTextField[][] cells = new JTextField[9][9];
    private final SudokuBoardViewModel viewModel;
    private final SudokuController controller;
    private final hintController hint;
    private final processController process;
    private final CheckController check;
    private final ForfeitController forfeitController;

    // Backwards-compatible no-arg constructor shows an empty board for quick tests
    public rankedSudokuBoardView() {
        this(null, null, null, null, null, null);
    }

    public rankedSudokuBoardView(SudokuBoardViewModel viewModel,
                                 SudokuController controller,
                                 hintController hint,
                                 processController process,
                                 CheckController check,
                                 ForfeitController forfeitController) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.hint = hint;
        this.process = process;
        this.check = check;
        this.forfeitController = forfeitController;

        setLayout(new BorderLayout());

        JLabel title = new JLabel("SUDOKU-THING (Ranked)", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(title, BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(9, 9));
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setFont(new Font("SansSerif", Font.BOLD, 28));

                final int fr = r;
                final int fc = c;

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
                    if (process != null) {
                        try {
                            String text = tf.getText();
                            int value = text.isEmpty() ? 0 : Integer.parseInt(text);
                            process.processMove(fr, fc, value);
                        } catch (NumberFormatException ex) {
                            tf.setText("");
                        }
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

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton hintBtn = new JButton("HINT");
        hintBtn.setPreferredSize(new Dimension(140, 60));
        hintBtn.addActionListener(e -> {
            if (hint != null && viewModel != null) hint.hint(viewModel.getBoard(), viewModel.getSolution());
        });

        JButton checkBtn = new JButton("CHECK");
        checkBtn.setPreferredSize(new Dimension(140, 60));
        checkBtn.addActionListener(e -> {
            if (check != null && viewModel != null) check.check(viewModel.getBoard(), viewModel.getSolution());
        });

        JButton forfeitBtn = new JButton("FORFEIT");
        forfeitBtn.setPreferredSize(new Dimension(140, 60));
        forfeitBtn.addActionListener(this);

        buttonPanel.add(hintBtn);
        buttonPanel.add(checkBtn);
        buttonPanel.add(forfeitBtn);

        add(grid, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        if (this.viewModel != null) {
            this.viewModel.addPropertyChangeListener(this);
            // initialize board from viewModel
            int[][] board = this.viewModel.getBoard();
            if (board != null) updateBoard(board);
        }
    }

    private void updateBoard(int[][] board) {
        int[][] initial = (viewModel != null) ? viewModel.getInitialBoard() : new int[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int v = board[r][c];
                JTextField tf = cells[r][c];
                tf.setText(v == 0 ? "" : String.valueOf(v));
                if (initial != null && initial[r][c] != 0) {
                    tf.setEditable(false);
                    tf.setBackground(new Color(230, 230, 230));
                } else {
                    tf.setEditable(true);
                    tf.setBackground(Color.WHITE);
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String event = e.getActionCommand();
        if ("FORFEIT".equals(event)) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to forfeit?",
                    "Confirm Forfeit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                forfeitController.showForfeit();
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("board".equals(evt.getPropertyName())) {
            int[][] board = (int[][]) evt.getNewValue();
            updateBoard(board);
        } else if ("incorrect".equals(evt.getPropertyName())) {
            boolean[][] incorrect = (boolean[][]) evt.getNewValue();
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    if (incorrect[r][c]) cells[r][c].setBackground(Color.RED);
                    else cells[r][c].setBackground(Color.WHITE);
                }
            }
        } else if ("error".equals(evt.getPropertyName())) {
            String message = (String) evt.getNewValue();
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}

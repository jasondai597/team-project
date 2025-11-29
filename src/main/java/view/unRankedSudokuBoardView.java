package view;

import interface_adapter.*;
import use_case.processUserMoves.ProcessInputData;

import javax.swing.*;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class unRankedSudokuBoardView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JTextField[][] cells = new JTextField[9][9];
    private final SudokuBoardViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    // Controllers
    private final SudokuController controller;
    private final SaveGameController saveController; // NEW
    private final hintController hint;
    private final processController process;
    private final CheckController check;
    private final ForfeitController forfeitController;

    private final String viewName = "unranked";

    public unRankedSudokuBoardView(SudokuBoardViewModel viewModel,
                                   ViewManagerModel viewManagerModel,
                                   SudokuController controller,
                                   SaveGameController saveController, // NEW
                                   hintController hintController,
                                   processController process,
                                   CheckController check,
                                   ForfeitController forfeitController) {

        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.controller = controller;
        this.saveController = saveController;
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

        // --- GRID GENERATION ---
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                JTextField tf = new JTextField();
                tf.setHorizontalAlignment(JTextField.CENTER);
                tf.setOpaque(true);
                tf.setBackground(Color.WHITE);
                tf.setFont(new Font("SansSerif", Font.BOLD, 30));
                final int finalR = r;
                final int finalC = c;

                ((javax.swing.text.AbstractDocument) tf.getDocument())
                        .setDocumentFilter(new DocumentFilter() {
                            @Override
                            public void replace(FilterBypass fb, int offset, int length,
                                                String text, javax.swing.text.AttributeSet attrs)
                                    throws javax.swing.text.BadLocationException {
                                if (text == null || text.isEmpty()) {
                                    super.replace(fb, offset, length, text, attrs);
                                    return;
                                }
                                if (text.matches("[1-9]") && fb.getDocument().getLength() - length < 1) {
                                    super.replace(fb, offset, length, text, attrs);
                                }
                            }
                        });

                // Move Processing
                tf.addActionListener(e -> {
                    try {
                        String text = tf.getText();
                        int value = text.isEmpty() ? 0 : Integer.parseInt(text);
                        ProcessInputData inputData = new ProcessInputData(finalR, finalC, value);
                        process.processMove(inputData);
                        // REMOVED auto-save here
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

        int[][] current = viewModel.getBoard();
        if (current != null) {
            updateBoard(current);
        }

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton back = new JButton("BACK");
        back.setPreferredSize(new Dimension(120, 50));
        back.addActionListener(this);

        JButton saveButton = new JButton("SAVE"); // NEW BUTTON
        saveButton.setPreferredSize(new Dimension(120, 50));
        saveButton.addActionListener(this);

        JButton hintButton = new JButton("HINT");
        hintButton.setPreferredSize(new Dimension(120, 50));
        hintButton.addActionListener(this);

        JButton checkButton = new JButton("CHECK");
        checkButton.setPreferredSize(new Dimension(120, 50));
        checkButton.addActionListener(this);

        JButton forfeitButton = new JButton("FORFEIT");
        forfeitButton.setPreferredSize(new Dimension(120, 50));
        forfeitButton.addActionListener(this);

        buttonPanel.add(back);
        buttonPanel.add(saveButton); // Add to panel
        buttonPanel.add(hintButton);
        buttonPanel.add(checkButton);
        buttonPanel.add(forfeitButton);

        add(grid, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return viewName;
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

            case "SAVE": // NEW CASE
                String gameId = viewModel.getGameId();
                if (gameId == null || gameId.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Error: No Game ID found to save.", "Save Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    saveController.save(gameId, viewModel.getBoard());
                }
                break;

            case "BACK":
                // Just navigate, DO NOT SAVE
                viewManagerModel.setState("main");
                viewManagerModel.firePropertyChange();
                break;

            case "FORFEIT":
                int result = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to forfeit? (Progress will NOT be saved)",
                        "Confirm Forfeit",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (result == JOptionPane.YES_OPTION) {
                    // Just navigate, DO NOT SAVE
                    forfeitController.showForfeit();
                }
                break;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "board":
                int[][] board = (int[][]) evt.getNewValue();
                updateBoard(board);
                break;

            case "incorrect":
                boolean[][] incorrect = (boolean[][]) evt.getNewValue();
                for (int r = 0; r < 9; r++) {
                    for (int c = 0; c < 9; c++) {
                        JTextField tf = cells[r][c];
                        if (incorrect[r][c]) {
                            tf.setBackground(Color.RED);
                        } else {
                            tf.setBackground(Color.WHITE);
                        }
                    }
                }
                break;

            case "error":
                String message = (String) evt.getNewValue();
                JOptionPane.showMessageDialog(this, message,
                        "Error", JOptionPane.ERROR_MESSAGE);
                break;

            case "success": // NEW: Handle Save Success
                String successMsg = (String) evt.getNewValue();
                JOptionPane.showMessageDialog(this, successMsg,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                break;
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
                    tf.setBackground(new Color(230, 230, 230));
                } else {
                    tf.setEditable(true);
                    tf.setBackground(Color.WHITE);
                }
            }
        }
    }
}
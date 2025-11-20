package view;

import API.SudokuApiClient;
import data_access.SudokuRepositoryImpl;
import interface_adapter.SudokuBoardViewModel;
import interface_adapter.SudokuController;
import interface_adapter.SudokuPresenter;
import interface_adapter.hintController;
import use_case.LoadingSudoku.LoadSudokuInteractor;
import use_case.hints.HintInteractor;
import interface_adapter.HintPresenter;

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
    public unRankedSudokuBoardView(SudokuBoardViewModel viewModel, SudokuController controller, hintController hintController) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.hint = hintController;
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
                hint.hint();
                break;
            case "CHECK":

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
        if ("board".equals(evt.getPropertyName())) {
            int[][] board = (int[][]) evt.getNewValue();
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    cells[r][c].setText(board[r][c] == 0 ? "" : String.valueOf(board[r][c]));
                }
            }
        } else if ("error".equals(evt.getPropertyName())) {
            String message = (String) evt.getNewValue();
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }


    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SudokuApiClient apiClient = new SudokuApiClient();
            SudokuRepositoryImpl repo = new SudokuRepositoryImpl(apiClient);

            SudokuBoardViewModel viewModel = new SudokuBoardViewModel();
            SudokuPresenter presenter = new SudokuPresenter(viewModel);
            LoadSudokuInteractor interactor = new LoadSudokuInteractor(repo, presenter);
            SudokuController controller = new SudokuController(interactor);

            HintPresenter hintPresenter = new HintPresenter(viewModel);
            HintInteractor hintinteractor = new HintInteractor(viewModel, hintPresenter);
            hintController hint = new hintController(hintinteractor);

            unRankedSudokuBoardView view = new unRankedSudokuBoardView(viewModel, controller, hint);
            controller.loadPuzzle("easy");

            JFrame frame = new JFrame("Sudoku");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 900);
            frame.add(view);
            frame.setVisible(true);

        });

    }

}

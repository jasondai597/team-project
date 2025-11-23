package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class SudokuBoardViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private int[][] initialBoard;
    private int[][] board;
    private int[][] solution;
    private boolean[][] incorrectCells;
    private String errorMessage;

    public void setInitialBoard(int[][] initialBoard) {
        int[][] old = this.initialBoard;
        this.initialBoard = initialBoard;
        support.firePropertyChange("initialBoard", old, initialBoard);
    }

    public int[][] getInitialBoard() {
        return initialBoard;
    }


    public void setBoard(int[][] board) {
        int[][] oldBoard = this.board;
        this.board = board;
        support.firePropertyChange("board", oldBoard, board);
    }
    public void setSolution(int[][] solution) {
        int [][] oldSolution = this.solution;
        this.solution = solution;
        support.firePropertyChange("solution", oldSolution, solution);
    }

    public void setErrorMessage(String message) {
        String old = this.errorMessage;
        this.errorMessage = message;
        support.firePropertyChange("error", old, message);
    }

    public int[][] getBoard() {
        return board;
    }
    public int[][] getSolution() {
        return solution;
    }
    public boolean[][] getIncorrectCells() {
        return incorrectCells;
    }

    public void setIncorrectCells(boolean[][] incorrectCells) {
        boolean[][] old = this.incorrectCells;

        this.incorrectCells = new boolean[9][9];
        for (int i = 0; i < 9; i++) this.incorrectCells[i] = incorrectCells[i].clone();

        support.firePropertyChange("incorrect", old, this.incorrectCells);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }


    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }

}

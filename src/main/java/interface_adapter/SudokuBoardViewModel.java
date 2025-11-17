package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;


public class SudokuBoardViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private int[][] board;
    private String errorMessage;

    public void setBoard(int[][] board) {
        int[][] oldBoard = this.board;
        this.board = board;
        support.firePropertyChange("board", oldBoard, board);
    }

    public void setErrorMessage(String message) {
        String old = this.errorMessage;
        this.errorMessage = message;
        support.firePropertyChange("error", old, message);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }


}

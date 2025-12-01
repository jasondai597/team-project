package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SudokuBoardViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String gameId;
    private int[][] initialBoard;
    private int[][] board;
    private int[][] solution;
    private boolean[][] incorrectCells;
    private String errorMessage;
    private String difficulty;
    private long startTime;
    private boolean isForfeited = false;

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setInitialBoard(int[][] initialBoard) {
        int[][] old = this.initialBoard;
        this.initialBoard = copyBoard(initialBoard);
        support.firePropertyChange("initialBoard", old, this.initialBoard);
    }

    public int[][] getInitialBoard() {
        return copyBoard(initialBoard);
    }

    public void setBoard(int[][] board) {
        int[][] old = this.board;
        this.board = copyBoard(board);
        support.firePropertyChange("board", old, this.board);
    }

    public int[][] getBoard() {
        return copyBoard(board);
    }

    public void setSolution(int[][] solution) {
        int[][] old = this.solution;
        this.solution = copyBoard(solution);
        support.firePropertyChange("solution", old, this.solution);
    }

    public int[][] getSolution() {
        return copyBoard(solution);
    }

    public void setErrorMessage(String message) {
        String old = this.errorMessage;
        this.errorMessage = message;
        support.firePropertyChange("error", old, message);
    }

    public void setSuccessMessage(String message) {
        support.firePropertyChange("success", null, message);
    }

    public boolean[][] getIncorrectCells() {
        if (incorrectCells == null) return null;
        boolean[][] copy = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            copy[i] = incorrectCells[i].clone();
        }
        return copy;
    }

    public void setIncorrectCells(boolean[][] incorrectCells) {
        boolean[][] old = this.incorrectCells;
        this.incorrectCells = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            this.incorrectCells[i] = incorrectCells[i].clone();
        }
        support.firePropertyChange("incorrect", old, this.incorrectCells);
    }

    public void clearIncorrectCells() {
        boolean[][] cleared = new boolean[9][9];
        setIncorrectCells(cleared);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
        support.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getElapsedTimeMs() {
        return System.currentTimeMillis() - startTime;
    }

    public void setForfeited(boolean forfeited) {
        this.isForfeited = forfeited;
    }

    public boolean isForfeited() {
        return isForfeited;
    }

    private int[][] copyBoard(int[][] src) {
        if (src == null) return null;
        int[][] dst = new int[src.length][];
        for (int i = 0; i < src.length; i++) {
            dst[i] = src[i].clone();
        }
        return dst;
    }
}
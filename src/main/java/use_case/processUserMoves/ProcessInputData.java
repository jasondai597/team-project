package use_case.processUserMoves;

public class ProcessInputData {
    private int row;
    private int col;
    private int value;
    public ProcessInputData(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
    }

    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public int getValue() {
        return value;
    }

}

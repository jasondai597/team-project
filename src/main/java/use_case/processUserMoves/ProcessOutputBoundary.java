package use_case.processUserMoves;

public interface ProcessOutputBoundary {
    void presentSuccess(int row, int col, int value);
    void presentInvalidMove(String message);
}

package use_case.save;

public class SaveGameInputData {
    private final String gameId;
    private final int[][] currentBoard;

    public SaveGameInputData(String gameId, int[][] currentBoard) {
        this.gameId = gameId;
        this.currentBoard = currentBoard;
    }

    public String getGameId() { return gameId; }
    public int[][] getCurrentBoard() { return currentBoard; }
}
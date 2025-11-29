package interface_adapter;

import use_case.save.SaveGameInputBoundary;
import use_case.save.SaveGameInputData;

public class SaveGameController {
    private final SaveGameInputBoundary interactor;

    public SaveGameController(SaveGameInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void save(String gameId, int[][] board) {
        SaveGameInputData data = new SaveGameInputData(gameId, board);
        interactor.execute(data);
    }
}
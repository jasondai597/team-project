package use_case.save;

import entity.Game;
import use_case.game.GameDataAccess;
import interface_adapter.SudokuBoardViewModel;

public class SaveGameInteractor implements SaveGameInputBoundary {

    private final GameDataAccess gameDataAccess;
    private final SudokuBoardViewModel viewModel; // Using ViewModel to trigger success message

    public SaveGameInteractor(GameDataAccess gameDataAccess, SudokuBoardViewModel viewModel) {
        this.gameDataAccess = gameDataAccess;
        this.viewModel = viewModel;
    }

    @Override
    public void execute(SaveGameInputData data) {
        if (data.getGameId() == null) {
            viewModel.setErrorMessage("Cannot save: Game ID missing.");
            return;
        }

        // Note: You might want to pass difficulty/mode in InputData if they change.
        // For now, we assume standard CASUAL/Easy for saving updates.
        try {
            Game game = new Game(
                    data.getGameId(),
                    data.getCurrentBoard(),
                    "easy", // You might want to store difficulty in ViewModel too
                    "CASUAL",
                    0L
            );
            gameDataAccess.save(game);
            viewModel.setSuccessMessage("Game Saved Successfully!");
        } catch (Exception e) {
            viewModel.setErrorMessage("Save failed: " + e.getMessage());
        }
    }
}
package use_case.LoadingSudoku;

import entity.Game;
import entity.SudokuPuzzle;
import org.json.JSONObject;
import use_case.game.GameDataAccess;
import use_case.processUserMoves.ProcessInteractor; // Import ProcessInteractor

public class LoadSudokuInteractor implements LoadSudokuInputBoundary {

    private final SudokuRepository repo;
    private final LoadSudokuOutputBoundary presenter;
    private final GameDataAccess gameDataAccess;
    private final ProcessInteractor processInteractor; // 1. Add field

    // 2. Update Constructor to take 4 arguments
    public LoadSudokuInteractor(SudokuRepository repo,
                                LoadSudokuOutputBoundary presenter,
                                GameDataAccess gameDataAccess,
                                ProcessInteractor processInteractor) {
        this.repo = repo;
        this.presenter = presenter;
        this.gameDataAccess = gameDataAccess;
        this.processInteractor = processInteractor;
    }

    @Override
    public void execute(LoadSudokuInputData request) {
        try {
            JSONObject json = repo.fetchSudokuJSON(request.getDifficulty());

            int[][] initial = SudokuBoardParser.parse(json.getString("puzzle"));
            int[][] solution = SudokuBoardParser.parse(json.getString("solution"));

            SudokuPuzzle puzzle = new SudokuPuzzle(initial, solution, request.getDifficulty());

            // 3. IMPORTANT: Tell ProcessInteractor about the new puzzle
            // This prevents users from overwriting fixed numbers!
            if (processInteractor != null) {
                processInteractor.setPuzzle(puzzle);
            }

            // Save new game
            String currentGameId = gameDataAccess.generateId();
            gameDataAccess.save(new Game(
                    currentGameId,
                    initial,
                    request.getDifficulty(),
                    "CASUAL",
                    0L
            ));

            presenter.present(puzzle, currentGameId);

        } catch (Exception e) {
            e.printStackTrace();
            presenter.presentError("Failed to load board: " + e.getMessage());
        }
    }
}
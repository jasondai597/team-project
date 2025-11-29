package use_case.LoadingSudoku;

import entity.Game;
import entity.SudokuPuzzle;
import org.json.JSONObject;
import use_case.game.GameDataAccess;
import use_case.processUserMoves.ProcessInteractor;

public class LoadSudokuInteractor implements LoadSudokuInputBoundary {

    private final SudokuRepository repo;
    private final LoadSudokuOutputBoundary presenter;
    private final GameDataAccess gameDataAccess;
    private final ProcessInteractor processInteractor; // 1. Added Dependency

    public LoadSudokuInteractor(SudokuRepository repo,
                                LoadSudokuOutputBoundary presenter,
                                GameDataAccess gameDataAccess,
                                ProcessInteractor processInteractor) { // 2. Updated Constructor
        this.repo = repo;
        this.presenter = presenter;
        this.gameDataAccess = gameDataAccess;
        this.processInteractor = processInteractor;
    }

    @Override
    public void execute(LoadSudokuInputData request) {
        try {
            // 1. Fetch and Parse
            JSONObject json = repo.fetchSudokuJSON(request.getDifficulty());
            int[][] initial = SudokuBoardParser.parse(json.getString("puzzle"));
            int[][] solution = SudokuBoardParser.parse(json.getString("solution"));

            // 2. Create Puzzle Entity
            SudokuPuzzle puzzle = new SudokuPuzzle(initial, solution, request.getDifficulty());

            // 3. CRITICAL FIX: Tell the ProcessInteractor about the new puzzle
            // This ensures the user cannot overwrite fixed numbers (the "Silent Failure" bug)
            if (processInteractor != null) {
                processInteractor.setPuzzle(puzzle);
            }

            // 4. Generate ID and Save Initial State (Game Creation)
            String newGameId = gameDataAccess.generateId();
            Game newGame = new Game(
                    newGameId,
                    initial,
                    request.getDifficulty(),
                    "CASUAL",
                    0L
            );
            gameDataAccess.save(newGame);

            // 5. Present the new game AND the ID (so we can save it later)
            presenter.present(puzzle, newGameId);

        } catch (Exception e) {
            e.printStackTrace();
            presenter.presentError("Failed to load board: " + e.getMessage());
        }
    }
}
package use_case.LoadingSudoku;

import entity.Game;
import entity.SudokuPuzzle;
import org.json.JSONObject;
import use_case.game.GameDataAccess;

import java.util.List;

public class LoadSudokuInteractor implements LoadSudokuInputBoundary {

    private final SudokuRepository repo;
    private final LoadSudokuOutputBoundary presenter;
    private final GameDataAccess gameDataAccess;

    private String currentGameId;
    private String currentDifficulty = "easy";

    public LoadSudokuInteractor(SudokuRepository repo,
                                LoadSudokuOutputBoundary presenter,
                                GameDataAccess gameDataAccess) {
        this.repo = repo;
        this.presenter = presenter;
        this.gameDataAccess = gameDataAccess;
    }

    @Override
    public void execute(LoadSudokuInputData request) {
        try {
            JSONObject json = repo.fetchSudokuJSON(request.getDifficulty());

            int[][] initial = SudokuBoardParser.parse(json.getString("puzzle"));
            int[][] solution = SudokuBoardParser.parse(json.getString("solution"));

            SudokuPuzzle puzzle = new SudokuPuzzle(initial, solution, request.getDifficulty());
            currentDifficulty = request.getDifficulty();

            // SAVE new game (initial state)
            currentGameId = gameDataAccess.generateId();
            gameDataAccess.save(new Game(
                    currentGameId,
                    initial,
                    currentDifficulty,
                    "CASUAL",
                    0L
            ));

            presenter.present(puzzle);

        } catch (Exception e) {
            presenter.presentError("Failed to load board: " + e.getMessage());
        }
    }

    @Override
    public void saveCurrentGameState(int[][] board) {
        if (board == null || currentGameId == null) return;

        try {
            gameDataAccess.save(new Game(
                    currentGameId,
                    board,
                    currentDifficulty,
                    "CASUAL",
                    0L
            ));
            System.out.println("💾 Game state saved.");
        } catch (Exception e) {
            presenter.presentError("Failed to save game: " + e.getMessage());
        }
    }

    @Override
    public void resumeLastGame() {
        List<Game> all = gameDataAccess.listAll();
        if (all.isEmpty()) {
            presenter.presentError("No saved games found!");
            return;
        }

        Game last = all.get(0);

        currentGameId = last.getId();
        currentDifficulty = last.getDifficulty();

        presenter.presentLoadedBoard(last.getBoard());
    }
}

package use_case.LoadingSudoku;

import org.json.JSONObject;

import entity.Game;
import entity.SudokuPuzzle;
import use_case.game.GameDataAccess;
import use_case.processUserMoves.ProcessInteractor;

public class LoadSudokuInteractor implements LoadSudokuInputBoundary {

    private final SudokuRepository repo;
    private final LoadSudokuOutputBoundary presenter;
    private final GameDataAccess gameDataAccess;
    private final ProcessInteractor processInteractor;

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
            final JSONObject json = repo.fetchSudokuJSON(request.getDifficulty());

            final int[][] initial = SudokuBoardParser.parse(json.getString("puzzle"));
            final int[][] solution = SudokuBoardParser.parse(json.getString("solution"));

            final SudokuPuzzle puzzle = new SudokuPuzzle(initial, solution, request.getDifficulty());

            if (processInteractor != null) {
                processInteractor.setPuzzle(puzzle);
            }

            final String currentGameId = gameDataAccess.generateId();
            gameDataAccess.save(new Game(
                    currentGameId,
                    initial,
                    request.getDifficulty(),
                    "CASUAL",
                    0L
            ));

            presenter.present(puzzle, currentGameId);

        }
        catch (Exception e) {
            e.printStackTrace();
            presenter.presentError("Failed to load board: " + e.getMessage());
        }
    }
}
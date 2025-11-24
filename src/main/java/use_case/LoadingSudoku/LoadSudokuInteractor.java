package use_case.LoadingSudoku;
import entity.SudokuPuzzle;
import org.json.JSONObject;
import use_case.game.GameDataAccess;
import entity.Game;
import java.util.List;

public class LoadSudokuInteractor implements LoadSudokuInputBoundary {
    private final SudokuRepository repository;
    private final LoadSudokuOutputBoundary presenter;
    private final GameDataAccess gameDataAccess;  //
    private SudokuPuzzle currentPuzzle;
    private String currentGameId;
    private String currentDifficulty;
    private String currentMode;

    public LoadSudokuInteractor(SudokuRepository repository,
                                LoadSudokuOutputBoundary presenter,
                                GameDataAccess gameDataAccess) {
        this.repository = repository;
        this.presenter = presenter;
        this.gameDataAccess = gameDataAccess;
    }

    public void saveCurrentGameState(int[][] currentBoard) {
        // If we don't have persistence or no loaded game, do nothing
        if (gameDataAccess == null || currentGameId == null) {
            return;
        }

        int[][] boardCopy = copyBoard(currentBoard);

        // For now we keep elapsedMs = 0; you can add a timer later
        Game updated = new Game(
                currentGameId,
                boardCopy,
                currentDifficulty,
                currentMode,
                0L
        );

        gameDataAccess.save(updated);
    }

    public void resumeLastGame() {
        if (gameDataAccess == null) {
            presenter.presentError("No persistence available.");
            return;
        }

        List<Game> games = gameDataAccess.listAll();
        if (games.isEmpty()) {
            presenter.presentError("No saved games found.");
            return;
        }

        // Most recent game
        Game last = games.get(0);

        this.currentGameId = last.getId();
        this.currentDifficulty = last.getDifficulty();
        this.currentMode = last.getMode();

        presenter.presentLoadedBoard(last.getBoard());
    }

    public void execute(LoadSudokuInputData request) {
        try {
            JSONObject json = repository.fetchSudokuJSON(request.getDifficulty());
            String puzzle = json.getString("puzzle");
            String solution = json.getString("solution");
            int[][] board = SudokuBoardParser.parse(puzzle);
            int[][] solutionBoard = SudokuBoardParser.parse(solution);
            currentPuzzle = new SudokuPuzzle(board, solutionBoard, request.getDifficulty());

            this.currentDifficulty = request.getDifficulty();
            this.currentMode = "CASUAL";

            if (gameDataAccess != null) {
                String gameId = gameDataAccess.generateId();
                this.currentGameId = gameId;

                int[][] currentCopy = copyBoard(board);

                Game game = new Game(
                        gameId,
                        currentCopy,
                        currentDifficulty,
                        currentMode,
                        0L
                );

                gameDataAccess.save(game);
            }

            presenter.present(currentPuzzle);
        } catch (Exception e) {
            presenter.presentError("Failed to load board: " + e.getMessage());
        }
    }

    private int[][] copyBoard(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }


    public SudokuPuzzle getCurrentPuzzle() {
        return currentPuzzle;
    }
}



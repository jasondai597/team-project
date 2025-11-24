package use_case.LoadingSudoku;
import entity.SudokuPuzzle;
import org.json.JSONObject;
import use_case.game.GameDataAccess;
import entity.Game;

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

    public LoadSudokuInteractor(SudokuRepository repository,
                                LoadSudokuOutputBoundary presenter) {
        this(repository, presenter, null);
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



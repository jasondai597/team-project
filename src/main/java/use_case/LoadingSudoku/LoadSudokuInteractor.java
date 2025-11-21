package use_case.LoadingSudoku;
import entity.SudokuPuzzle;
import org.json.JSONObject;
import entity.Game;
import use_case.game.GameDataAccess;

public class LoadSudokuInteractor {
    private final SudokuRepository repository;
    private final LoadSudokuOutputBoundary presenter;
    private final GameDataAccess gameDataAccess;
    private SudokuPuzzle currentPuzzle;

    public LoadSudokuInteractor(SudokuRepository repository,
                                LoadSudokuOutputBoundary presenter,
                                GameDataAccess gameDataAccess) {
        this.repository = repository;
        this.presenter = presenter;
        this.gameDataAccess = gameDataAccess;
    }
    // old constructor kept for compatibility
    public LoadSudokuInteractor(SudokuRepository repository,
                                LoadSudokuOutputBoundary presenter) {
        this(repository, presenter, null);
    }

    public void execute(LoadSudokuRequestModel request) {
        try {
            JSONObject json = repository.fetchSudokuJSON(request.getDifficulty());
            String puzzle = json.getString("puzzle");
            String solution = json.getString("solution");

            int[][] board = SudokuBoardParser.parse(puzzle);
            int[][] solutionBoard = SudokuBoardParser.parse(solution);
            currentPuzzle = new SudokuPuzzle(board, solutionBoard, request.getDifficulty());

            // 🔹 use GameDataAccess if provided
            if (gameDataAccess != null) {
                String gameId = gameDataAccess.generateId();

                int[][] currentCopy = copyBoard(board);

                Game game = new Game(
                        gameId,
                        currentCopy,
                        request.getDifficulty(),
                        "CASUAL",
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

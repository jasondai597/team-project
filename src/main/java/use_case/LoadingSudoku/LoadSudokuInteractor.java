package use_case.LoadingSudoku;

import entity.SudokuPuzzle;
import org.json.JSONObject;
import use_case.game.GameDataAccess;

public class LoadSudokuInteractor {
    private final SudokuRepository repository;
    private final LoadSudokuOutputBoundary presenter;
    private final GameDataAccess gameDataAccess;  // 🔹 new field
    private SudokuPuzzle currentPuzzle;

    // 🔹 New main constructor (we'll start using this later)
    public LoadSudokuInteractor(SudokuRepository repository,
                                LoadSudokuOutputBoundary presenter,
                                GameDataAccess gameDataAccess) {
        this.repository = repository;
        this.presenter = presenter;
        this.gameDataAccess = gameDataAccess;
    }

    // 🔹 Old constructor kept for compatibility (uses null for now)
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

            presenter.present(currentPuzzle);
        } catch (Exception e) {
            presenter.presentError("Failed to load board: " + e.getMessage());
        }
    }

    public SudokuPuzzle getCurrentPuzzle() {
        return currentPuzzle;
    }
}

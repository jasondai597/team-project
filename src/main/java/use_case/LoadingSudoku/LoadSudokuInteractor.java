package use_case.LoadingSudoku;

import entity.SudokuPuzzle;
import org.json.JSONObject;

public class LoadSudokuInteractor {
    private final SudokuRepository repository;
    private final LoadSudokuOutputBoundary presenter;

    public LoadSudokuInteractor(SudokuRepository repository, LoadSudokuOutputBoundary presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    public void execute(LoadSudokuRequestModel request) {
        try {
            JSONObject json = repository.fetchSudokuJSON(request.getDifficulty());
            String puzzle = json.getString("puzzle");
            String solution = json.getString("solution");
            int[][] board = SudokuBoardParser.parse(puzzle);
            int[][] solutionBoard = SudokuBoardParser.parse(solution);
            SudokuPuzzle game = new SudokuPuzzle(board, solutionBoard, request.getDifficulty());

            presenter.present(game);
        } catch (Exception e) {
            presenter.presentError("Failed to load board: " + e.getMessage());
        }
    }

}

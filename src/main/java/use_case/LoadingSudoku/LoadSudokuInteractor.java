package use_case.LoadingSudoku;

import entity.SudokuPuzzle;

public class LoadSudokuInteractor {
    private final SudokuRepository repository;
    private final LoadSudokuOutputBoundary presenter;

    public LoadSudokuInteractor(SudokuRepository repository, LoadSudokuOutputBoundary presenter) {
        this.repository = repository;
        this.presenter = presenter;
    }

    public void execute(LoadSudokuRequestModel request) {
        try {
            String puzzle = repository.fetchSudokuString(request.getDifficulty());
            int[][] board = SudokuBoardParser.parse(puzzle);
            SudokuPuzzle game = new SudokuPuzzle(board, null, request.getDifficulty());

            presenter.present(game);
        } catch (Exception e) {
            presenter.presentError("Failed to load board: " + e.getMessage());
        }
    }

}

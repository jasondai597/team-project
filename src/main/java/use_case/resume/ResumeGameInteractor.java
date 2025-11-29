package use_case.resume;

import entity.Game;
import entity.SudokuPuzzle;
import use_case.game.GameDataAccess;
import use_case.LoadingSudoku.LoadSudokuOutputBoundary;
import use_case.processUserMoves.ProcessInteractor;
import java.util.List;

public class ResumeGameInteractor implements ResumeGameInputBoundary {

    private final GameDataAccess gameDataAccess;
    private final LoadSudokuOutputBoundary presenter;
    private final ProcessInteractor processInteractor;

    public ResumeGameInteractor(GameDataAccess gameDataAccess,
                                LoadSudokuOutputBoundary presenter,
                                ProcessInteractor processInteractor) {
        this.gameDataAccess = gameDataAccess;
        this.presenter = presenter;
        this.processInteractor = processInteractor;
    }

    @Override
    public void execute() {
        List<Game> all = gameDataAccess.listAll();
        if (all.isEmpty()) {
            presenter.presentError("No saved games found!");
            return;
        }

        Game last = all.get(0);

        // Update ProcessInteractor with a dummy puzzle so moves work
        // (Ideally, you save the 'initial' board in the Game entity to restore rules fully)
        SudokuPuzzle puzzle = new SudokuPuzzle(last.getBoard(), null, last.getDifficulty());
        processInteractor.setPuzzle(puzzle);

        // This method in presenter needs to update the View Model's GameID too!
        presenter.presentLoadedBoard(last.getBoard());

        // We need a way to set the GameID in ViewModel.
        // We can cast the presenter or add a method to the OutputBoundary.
        // For now, let's assume presentLoadedBoard handles it or we do it via a specialized presenter.
    }
}
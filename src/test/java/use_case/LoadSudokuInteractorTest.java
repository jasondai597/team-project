package use_case;

import entity.SudokuPuzzle;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import use_case.LoadingSudoku.LoadSudokuInputData;
import use_case.LoadingSudoku.LoadSudokuInteractor;
import use_case.LoadingSudoku.LoadSudokuOutputBoundary;
import use_case.LoadingSudoku.SudokuRepository;
import use_case.game.GameDataAccess;
import entity.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class LoadSudokuInteractorTest {

    // ---------- Fake Dependencies ----------

    static class FakeRepo implements SudokuRepository {
        JSONObject toReturn;
        boolean shouldThrow = false;

        @Override
        public String fetchSudokuString(String difficulty) throws Exception {
            return "";
        }

        @Override
        public JSONObject fetchSudokuJSON(String difficulty) throws Exception {
            if (shouldThrow) throw new Exception("repo failure");
            return toReturn;
        }
    }

    static class FakePresenter implements LoadSudokuOutputBoundary {
        boolean presentCalled = false;
        boolean errorCalled = false;

        SudokuPuzzle receivedPuzzle;
        String errorMessage;

        @Override
        public void present(SudokuPuzzle puzzle, String gameId) {
            this.presentCalled = true;
            this.receivedPuzzle = puzzle;
        }

        @Override
        public void presentError(String message) {
            errorCalled = true;
            errorMessage = message;
        }

        @Override
        public void presentLoadedBoard(int[][] board) {
        }

    }

    static class FakeGameData implements GameDataAccess {
        @Override public String generateId() { return "game123"; }
        @Override public void save(Game game) {}

        @Override
        public Optional<Game> load(String gameId) {
            return Optional.empty();
        }

        @Override public List<Game> listAll() { return new ArrayList<>(); }
    }

    private JSONObject sampleJSON() {
        JSONObject obj = new JSONObject();
        obj.put("puzzle", "0".repeat(81));
        obj.put("solution", "1".repeat(81));
        return obj;
    }


    @Test
    void testExecuteSuccess() {
        FakeRepo repo = new FakeRepo();
        repo.toReturn = sampleJSON();

        FakePresenter presenter = new FakePresenter();
        FakeGameData data = new FakeGameData();

        // FIX: Added 'null' as the 4th argument (ProcessInteractor)
        LoadSudokuInteractor interactor =
                new LoadSudokuInteractor(repo, presenter, data, null);

        LoadSudokuInputData request = new LoadSudokuInputData("medium");
        interactor.execute(request);

        assertTrue(presenter.presentCalled);
        assertFalse(presenter.errorCalled);

        SudokuPuzzle puzzle = presenter.receivedPuzzle;
        assertNotNull(puzzle);
        assertEquals("medium", puzzle.getDifficulty());
    }

    @Test
    void testExecuteFailure() {
        FakeRepo repo = new FakeRepo();
        repo.shouldThrow = true;

        FakePresenter presenter = new FakePresenter();
        FakeGameData data = new FakeGameData();

        // FIX: Added 'null' as the 4th argument here too
        LoadSudokuInteractor interactor =
                new LoadSudokuInteractor(repo, presenter, data, null);

        interactor.execute(new LoadSudokuInputData("easy"));

        assertTrue(presenter.errorCalled);
        assertFalse(presenter.presentCalled);
    }
}
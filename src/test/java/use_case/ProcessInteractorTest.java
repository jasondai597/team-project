package use_case;

import entity.SudokuPuzzle;
import org.junit.jupiter.api.Test;
import use_case.processUserMoves.ProcessInteractor;
import use_case.processUserMoves.ProcessOutputBoundary;

import static org.junit.jupiter.api.Assertions.*;

class ProcessInteractorTest {

    static class TestPresenter implements ProcessOutputBoundary {
        boolean successCalled = false;
        int r, c, v;

        @Override
        public void presentSuccess(int row, int col, int value) {
            successCalled = true;
            r = row;
            c = col;
            v = value;
        }

        @Override
        public void presentInvalidMove(String message) {

        }
    }

    private SudokuPuzzle makePuzzle() {
        return new SudokuPuzzle(new int[9][9], new int[9][9], "easy");
    }

    // --- TESTS ---

    @Test
    void testExecuteSuccess() {
        TestPresenter presenter = new TestPresenter();
        SudokuPuzzle puzzle = makePuzzle();

        ProcessInteractor interactor = new ProcessInteractor(puzzle, presenter);

        interactor.execute(3, 4, 7);

        assertTrue(presenter.successCalled);
        assertEquals(3, presenter.r);
        assertEquals(4, presenter.c);
        assertEquals(7, presenter.v);

        assertEquals(7, puzzle.getInitial()[3][4]);
    }

    @Test
    void testNullPuzzle() {
        TestPresenter presenter = new TestPresenter();
        ProcessInteractor interactor = new ProcessInteractor(null, presenter);

        interactor.execute(2, 2, 5);

        assertFalse(presenter.successCalled);
    }

    @Test
    void testOutOfBounds() {
        TestPresenter presenter = new TestPresenter();
        SudokuPuzzle puzzle = makePuzzle();

        ProcessInteractor interactor = new ProcessInteractor(puzzle, presenter);

        interactor.execute(-1, 0, 9);
        interactor.execute(0, 20, 9);

        assertFalse(presenter.successCalled);
    }

    @Test
    void testSetPuzzleReplacesPuzzle() {
        TestPresenter presenter = new TestPresenter();

        SudokuPuzzle oldPuzzle = makePuzzle();
        SudokuPuzzle newPuzzle = makePuzzle();

        ProcessInteractor interactor = new ProcessInteractor(oldPuzzle, presenter);

        interactor.setPuzzle(newPuzzle);
        interactor.execute(0, 0, 3);

        assertEquals(3, newPuzzle.getInitial()[0][0]);
        assertEquals(0, oldPuzzle.getInitial()[0][0]);
    }
}

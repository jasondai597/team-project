package use_case;

import org.junit.jupiter.api.Test;
import use_case.hints.HintInteractor;
import use_case.hints.HintOutputBoundary;
import use_case.hints.hintInputData;

import static org.junit.jupiter.api.Assertions.*;

class HintInteractorTest {

    static class TestPresenter implements HintOutputBoundary {

        boolean hintCalled = false;
        boolean noHintCalled = false;

        int receivedRow;
        int receivedCol;
        int receivedValue;

        @Override
        public void presentHint(int r, int c, int val) {
            hintCalled = true;
            receivedRow = r;
            receivedCol = c;
            receivedValue = val;
        }

        @Override
        public void presentNoHint() {
            noHintCalled = true;
        }
    }

    private int[][] board(int value) {
        int[][] b = new int[9][9];
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                b[r][c] = value;
            }
        }
        return b;
    }

    @Test
    void testHintIsProvidedWhenFirstIncorrectCellFound() {
        TestPresenter presenter = new TestPresenter();
        HintInteractor interactor = new HintInteractor(presenter);

        int[][] board = board(5);
        int[][] solution = board(5);

        solution[1][2] = 9;

        hintInputData inputData = new hintInputData(board, solution);
        interactor.execute(inputData);

        assertTrue(presenter.hintCalled);
        assertFalse(presenter.noHintCalled);

        assertEquals(1, presenter.receivedRow);
        assertEquals(2, presenter.receivedCol);
        assertEquals(9, presenter.receivedValue);
    }

    @Test
    void testNoHintWhenBoardMatchesSolution() {
        TestPresenter presenter = new TestPresenter();
        HintInteractor interactor = new HintInteractor(presenter);

        int[][] board = board(7);
        int[][] solution = board(7);

        hintInputData inputData = new hintInputData(board, solution);
        interactor.execute(inputData);

        assertFalse(presenter.hintCalled);
        assertTrue(presenter.noHintCalled);
    }
}

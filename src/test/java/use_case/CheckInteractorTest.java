package use_case;
import org.junit.jupiter.api.Test;
import use_case.Check.CheckInteractor;
import use_case.Check.CheckOutputBoundary;
import use_case.Check.CheckInputData;

import static org.junit.jupiter.api.Assertions.*;

public class CheckInteractorTest {
    static class TestPresenter implements CheckOutputBoundary {
        boolean winCalled = false;
        boolean answerCalled = false;
        boolean[][] receivedIncorrectCells = null;

        @Override
        public void presentWin() {
            winCalled = true;
        }

        @Override
        public void presentAnswer(boolean[][] incorrectCells) {
            answerCalled = true;
            receivedIncorrectCells = incorrectCells;
        }
    }

    // Helper to create a 9x9 board
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
    void testAllCorrectCallsPresentWin() {
        TestPresenter presenter = new TestPresenter();
        CheckInteractor interactor = new CheckInteractor(presenter);

        int[][] correctBoard = board(5);
        int[][] solution = board(5);

        CheckInputData input = new CheckInputData(correctBoard, solution);
        interactor.check(input);

        assertTrue(presenter.winCalled);
        assertFalse(presenter.answerCalled);
    }

    @Test
    void testIncorrectCellsCallsPresentAnswer() {
        TestPresenter presenter = new TestPresenter();
        CheckInteractor interactor = new CheckInteractor(presenter);

        int[][] playerBoard = board(5);
        int[][] solution = board(5);

        playerBoard[2][3] = 9;

        CheckInputData input = new CheckInputData(playerBoard, solution);
        interactor.check(input);

        assertFalse(presenter.winCalled);
        assertTrue(presenter.answerCalled);

        boolean[][] incorrect = presenter.receivedIncorrectCells;
        assertNotNull(incorrect);
        assertTrue(incorrect[2][3]);
        assertFalse(incorrect[0][0]);
    }

}

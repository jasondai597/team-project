package interface_adapter;

import use_case.hints.HintInputBoundary;
import use_case.hints.hintInputData;

public class hintController {
    private final HintInputBoundary interactor;

    public hintController(HintInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void hint(int[][] board, int[][] solution) {
        final hintInputData inputData = new hintInputData(board, solution);
        interactor.execute(inputData);
    }

}

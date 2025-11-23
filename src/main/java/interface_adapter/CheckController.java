package interface_adapter;

import use_case.Check.CheckInputData;
import use_case.Check.checkInputBoundary;

public class CheckController {
    private final checkInputBoundary interactor;

    public CheckController(checkInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void check(int[][] board, int[][] solution){
        final CheckInputData inputData = new CheckInputData(board, solution);
        interactor.check(inputData);
    }
}

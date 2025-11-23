package interface_adapter;

import use_case.processUserMoves.ProcessInputBoundary;
import use_case.processUserMoves.ProcessInputData;

public class processController {
    private final ProcessInputBoundary interactor;
    public processController(ProcessInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void processMove(ProcessInputData inputData){
        int row = inputData.getRow();
        int col = inputData.getCol();
        int value = inputData.getValue();
        interactor.execute(row, col, value);
    }
}

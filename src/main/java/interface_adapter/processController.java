package interface_adapter;

import use_case.processUserMoves.ProcessInteractor;

public class processController {
    private final ProcessInteractor interactor;
    public processController(ProcessInteractor interactor) {
        this.interactor = interactor;
    }
    public void processMove(int row, int col, int value){
        interactor.execute(row, col, value);
    }
}

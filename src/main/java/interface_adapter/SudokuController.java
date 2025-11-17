package interface_adapter;

import use_case.LoadingSudoku.LoadSudokuInteractor;
import use_case.LoadingSudoku.LoadSudokuRequestModel;
public class SudokuController {
    private final LoadSudokuInteractor interactor;
    public SudokuController(LoadSudokuInteractor interactor) {
        this.interactor = interactor;
    }

    public void loadPuzzle(String difficulty) {
        interactor.execute(new LoadSudokuRequestModel(difficulty));
    }

}

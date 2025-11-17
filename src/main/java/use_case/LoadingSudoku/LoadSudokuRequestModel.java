package use_case.LoadingSudoku;

public class LoadSudokuRequestModel {
    private final String difficulty;

    public LoadSudokuRequestModel(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

}

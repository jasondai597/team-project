package use_case.LoadingSudoku;

public class LoadSudokuInputData {
    private final String difficulty;

    public LoadSudokuInputData(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }

}

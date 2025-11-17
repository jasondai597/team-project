package use_case.LoadingSudoku;

public interface SudokuRepository {
    String fetchSudokuString(String difficulty) throws Exception;
}

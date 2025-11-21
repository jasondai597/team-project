package use_case.LoadingSudoku;

import org.json.JSONObject;

public interface SudokuRepository {
    String fetchSudokuString(String difficulty) throws Exception;
    JSONObject fetchSudokuJSON(String difficulty) throws Exception;
}

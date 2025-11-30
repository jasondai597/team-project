package use_case.LoadingSudoku;

import org.json.JSONObject;

public interface SudokuRepository {
    /**
     * Fetches the sudoku string for the board from the API.
     * @param difficulty
     *      The difficulty of the board.
     * @return
     *      Returns a String that represents the board.
     * @throws Exception
     *      Throws exception in case of something wrong happening.
     */
    String fetchSudokuString(String difficulty) throws Exception;

    /**
     * Fetches the SudokuJSON as a whole.
     * @param difficulty
     *      Difficulty chosen by user.
     * @return
     *      a JSONObject that contains the board, the solution and the difficulty.
     * @throws Exception
     *      Throws exception in case of something wrong happening.
     */
    JSONObject fetchSudokuJSON(String difficulty) throws Exception;
}

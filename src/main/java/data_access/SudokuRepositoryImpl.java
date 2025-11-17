package data_access;


import API.SudokuApiClient;
import org.json.JSONObject;
import use_case.LoadingSudoku.SudokuRepository;

public class SudokuRepositoryImpl implements SudokuRepository {

    private final SudokuApiClient apiClient;

    public SudokuRepositoryImpl(SudokuApiClient apiClient) {
        this.apiClient = apiClient;
    }


    @Override
    public String fetchSudokuString(String difficulty) throws Exception {
        JSONObject json = apiClient.fetchPuzzle(difficulty);

        String puzzle = json.getString("puzzle");
        //System.out.println("Puzzle: " + puzzle);
        return puzzle;
    }

}


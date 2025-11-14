package API;

import okhttp3.*;
import org.json.JSONObject;

public class SudokuApiClient {

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static JSONObject fetchPuzzle(String difficulty) throws Exception {

        JSONObject json = new JSONObject();
        json.put("difficulty", difficulty);
        json.put("solution", true);
        json.put("array", false);

        RequestBody body = RequestBody.create(json.toString(), JSON);

        Request request = new Request.Builder()
                .url("https://youdosudoku.com/api/")
                .post(body)
                .header("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new RuntimeException("API error: " + response.code());

            String responseBody = response.body().string();
            return new JSONObject(responseBody);
        }
    }


}

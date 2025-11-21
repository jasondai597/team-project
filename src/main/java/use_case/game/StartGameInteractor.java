package use_case.game;

import entity.Game;

public class StartGameInteractor {

    private final GameDataAccess gameDataAccess;

    public StartGameInteractor(GameDataAccess gameDataAccess) {
        this.gameDataAccess = gameDataAccess;
    }

    public Game startNewGame(int[][] initialBoard,
                             String difficulty,
                             String mode) {

        // 1. Generate a new ID
        String id = gameDataAccess.generateId();

        // 2. Create a fresh Game entity
        Game game = new Game(
                id,
                copyBoard(initialBoard),
                difficulty,
                mode,
                0L
        );

        // 3. Save it
        gameDataAccess.save(game);

        // 4. Return the Game (controller/UI can use id or entire game)
        return game;
    }

    private int[][] copyBoard(int[][] original) {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            copy[i] = original[i].clone();
        }
        return copy;
    }
}

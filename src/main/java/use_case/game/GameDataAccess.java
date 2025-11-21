package use_case.game;

import entity.Game;

import java.util.List;
import java.util.Optional;

public interface GameDataAccess {

    // Create IDs when starting a new game
    String generateId();

    // Save or update a game
    void save(Game game);

    // Load by gameId
    Optional<Game> load(String gameId);

    // For history: later you can change to listByUser(userId)
    List<Game> listAll();
}

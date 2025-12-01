package use_case.game;

import java.util.List;
import java.util.Optional;

import entity.Game;
import use_case.forfeit.ForfeitDataAccessInterface;

public interface GameDataAccess extends ForfeitDataAccessInterface {

    // Create IDs when starting a new game
    String generateId();

    // Save or update a game
    @Override
    void save(Game game);

    // Load by gameId
    @Override
    Optional<Game> load(String gameId);

    // For history: later you can change to listByUser(userId)
    List<Game> listAll();

    // Delete a game by gameId (inherited from ForfeitDataAccessInterface)
    @Override
    void delete(String gameId);
}

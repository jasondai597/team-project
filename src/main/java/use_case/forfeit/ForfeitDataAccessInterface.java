package use_case.forfeit;

import java.util.Optional;

import entity.Game;

/**
 * Data Access Interface for the Forfeit use case.
 */
public interface ForfeitDataAccessInterface {
    /**
     * Deletes a game from storage.
     * @param gameId
     */
    void delete(String gameId);

    /**
     * Loads a game from storage.
     * @param gameId
     * @return
     */
    Optional<Game> load(String gameId);

    /**
     * Saves a game to storage.
     * @param game
     */
    void save(Game game);
}


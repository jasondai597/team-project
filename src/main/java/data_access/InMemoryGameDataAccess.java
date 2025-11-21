package data_access;

import entity.Game;
import use_case.game.GameDataAccess;

import java.util.*;

public class InMemoryGameDataAccess implements GameDataAccess {

    // simple in-memory store
    private final Map<String, Game> store = new HashMap<>();

    @Override
    public String generateId() {
        return UUID.randomUUID().toString();
    }

    @Override
    public void save(Game game) {
        store.put(game.getId(), game);
    }

    @Override
    public Optional<Game> load(String gameId) {
        return Optional.ofNullable(store.get(gameId));
    }

    @Override
    public List<Game> listAll() {
        // later: filter by user; for now, return all games
        return new ArrayList<>(store.values());
    }
}

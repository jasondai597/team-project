package data_access;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import entity.Game;
import use_case.game.GameDataAccess;

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
        return new ArrayList<>(store.values());
    }

    @Override
    public void delete(String gameId) {
        store.remove(gameId);
    }
}

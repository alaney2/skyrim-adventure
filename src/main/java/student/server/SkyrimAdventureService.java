package student.server;

import student.adventure.GameEngine;

import java.util.HashMap;
import java.util.Map;

public class SkyrimAdventureService implements AdventureService {
    private int id = 0;
    private Map<Integer, GameEngine> mapOfGamesRunning = new HashMap<>();

    public SkyrimAdventureService() { }

    public SkyrimAdventureService(int id) {
        this.id = id;
    }

    public int getId() { return id; }

    public Map<Integer, GameEngine> getMapOfGamesRunning() { return mapOfGamesRunning; }

    @Override
    public void reset() {
        id = 0;
        mapOfGamesRunning.clear();
    }

    @Override
    public int newGame() {
        GameEngine gameEngine = new GameEngine();
        try {
            mapOfGamesRunning.put(id, gameEngine);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id++;
    }

    @Override
    public GameStatus getGame(int id) {
        GameStatus gameStatus;
        if (mapOfGamesRunning.containsKey(id)) {
            GameEngine gameEngine = mapOfGamesRunning.get(id);
            gameEngine.createGameStatus();
            gameStatus = gameEngine.getGameStatus();
            gameStatus.setId(id);
        } else {
            gameStatus = new GameStatus(true, id, "", "", "", new AdventureState(""), new HashMap<>());
        }

        return gameStatus;
    }

    @Override
    public boolean destroyGame(int id) {
         return mapOfGamesRunning.remove(id) != null;
    }

    @Override
    public void executeCommand(int id, Command command) {
        GameEngine gameEngine = mapOfGamesRunning.get(id);
        String commandName = command.getCommandName();
        String commandValue = command.getCommandValue();
        gameEngine.getPlayer().executeCommand(new String[] {commandName, commandValue});
    }
}

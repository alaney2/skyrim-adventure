package student.server;

import student.adventure.GameEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SkyrimAdventureService implements AdventureService {
    private int id = 0;
    private Map<Integer, GameEngine> mapOfGamesRunning = new HashMap<>();

    public SkyrimAdventureService() { }

    @Override
    public void reset() {
        id = 0;
        mapOfGamesRunning = new HashMap<>();
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
        if (mapOfGamesRunning.containsKey(id)) {
            mapOfGamesRunning.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public void executeCommand(int id, Command command) {
        GameEngine gameEngine = mapOfGamesRunning.get(id);
        String commandName = command.getCommandName();
        String commandValue = command.getCommandValue();
        gameEngine.getPlayer().executeCommand(new String[] {commandName, commandValue});
    }

    public int getId() {
        return id;
    }
}

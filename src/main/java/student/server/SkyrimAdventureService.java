package student.server;

import student.adventure.GameEngine;
import student.adventure.Layout;
import student.adventure.Location;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkyrimAdventureService implements AdventureService {
    private int id = 0;
    private Map<Integer, GameEngine> mapOfGamesRunning = new HashMap<>();

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
            Map<String, List<String>> commandOptions = new HashMap<>();

            List<String> availableDirectionNames = Location.getAvailableDirectionNames
                    (gameEngine.getPlayer().getCurrentLocation());
            List<String> availableItemsToTake = Location.getAvailableItemsToTake
                    (gameEngine.getPlayer().getCurrentLocation());
            List<String> availableItemsToDrop = gameEngine.getPlayer()
                    .getAvailableItemsToDrop();

            if (availableDirectionNames.size() > 0) {
                commandOptions.put("go", availableDirectionNames);
            }
            if (availableItemsToTake.size() > 0) {
                commandOptions.put("take", availableItemsToTake);
            }
            if (availableItemsToDrop.size() > 0) {
                commandOptions.put("drop", availableItemsToDrop);
            }

            gameStatus = new GameStatus();
            gameStatus.setError(false);
            gameStatus.setId(id);
            gameStatus.setMessage(gameEngine.getPlayer().getCurrentLocation().getDescription());
            gameStatus.setImageUrl(gameEngine.getPlayer().getCurrentLocation().getImageUrl());
            gameStatus.setVideoUrl(GameEngine.getLayout().getVideoUrl());
            gameStatus.setState(new AdventureState());
            gameStatus.setCommandOptions(commandOptions);

        } else {
            gameStatus = new GameStatus(true, id, "", "", "", new AdventureState(), new HashMap<>());
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
}

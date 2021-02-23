package student.adventure;

import com.google.gson.Gson;
import student.server.AdventureState;
import student.server.GameStatus;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;


public class GameEngine {
    private static Layout layout;
    public static Map<String, Location> locationDictionary;
    public Player player;
    public GameStatus gameStatus;

    public static Layout getLayout() {
        return layout;
    }

    public Player getPlayer() {
        return player;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public GameEngine() {
        loadJson();
        createLocationDictionary();
        createPlayer();
        createGameStatus();
    }

    /**
     * Method that starts running the game.
     */
    public void runGame() {
        checkJsonForNull();
        Message.printGameIntro();

        System.out.println(player.getCurrentLocationInfo());

        String command;
        do {
            String[] arguments = UserInput.handleUserInput();
            command = arguments[0].toLowerCase();
            player.executeCommand(arguments);

            if (player.hasReachedEndingLocation()) {
                Message.printGameEnding();
                return;
            }
        }
        while (!command.equals("quit") && !command.equals("exit"));
    }

    /**
     * Loads JSON into existence.
     */
    public static void loadJson() {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader("src/main/resources/skyrim.json");
            layout = gson.fromJson(reader, Layout.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates an instance of player starting at starting location with nothing in inventory.
     */
    public void createPlayer() {
        player = new Player(locationDictionary.get(layout.getStartingLocation()), new ArrayList<>());
        player.setEndingLocation(locationDictionary.get(layout.getEndingLocation()));
    }

    /**
     * Creates a location dictionary which maps name of location to its location object.
     */
    public void createLocationDictionary() {
        locationDictionary = Layout.generateLocationDictionary(layout);
    }

    public void createGameStatus() {
        gameStatus = new GameStatus();
        player.generateCommandOptions();

        gameStatus.setError(false);
        gameStatus.setMessage(player.getCurrentLocation().getDescription());
        gameStatus.setImageUrl(player.getCurrentLocation().getImageUrl());
        gameStatus.setVideoUrl(layout.getVideoUrl());
        gameStatus.setState(new AdventureState());
        gameStatus.setCommandOptions(player.getCommandOptions());
    }

    /**
     * Checks if anything in the Json file is null and throws NullPointerException() if there is
     */
    public static void checkJsonForNull() {
        if (layout.getStartingLocation() == null) {
            throw new NullPointerException("Starting location");
        }
        if (layout.getEndingLocation() == null) {
            throw new NullPointerException("Ending location");
        }
        if (layout.getLocations() == null) {
            throw new NullPointerException("Locations");
        }
        for (int i = 0; i < layout.getLocations().size(); i++) {
            if (layout.getLocations().get(i) == null) {
                throw new NullPointerException("Location at index " + i);
            }
            if (layout.getLocations().get(i).getName() == null) {
                throw new NullPointerException("Location at index " + i);
            }
            if (layout.getLocations().get(i).getItems() == null) {
                throw new NullPointerException("Location at index " + i);
            }
            for (int itemIndex = 0; itemIndex < layout.getLocations().get(i).getItems().size(); itemIndex++) {
                if (layout.getLocations().get(i).getItems().get(itemIndex).getItemName() == null) {
                    throw new NullPointerException("Location at index " + i);
                }
            }
            if (layout.getLocations().get(i).getDirections() == null) {
                throw new NullPointerException("Location at index " + i);
            }
            for (int directionIndex = 0; directionIndex < layout.getLocations().get(i).getItems().size(); directionIndex++) {
                if (layout.getLocations().get(i).getItems().get(directionIndex).getItemName() == null) {
                    throw new NullPointerException("Location at index " + i);
                }
            }
            if (layout.getLocations().get(i).getDescription() == null) {
                throw new NullPointerException("Location at index " + i);
            }
        }
    }
}

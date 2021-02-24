package student.adventure;

import com.google.gson.Gson;
import student.server.AdventureState;
import student.server.GameStatus;

import java.io.FileReader;
import java.io.InvalidObjectException;
import java.io.Reader;
import java.util.*;


public class GameEngine {
    private static Layout layout;
    private static Map<String, Location> locationDictionary;
    private Player player;
    private GameStatus gameStatus;

    public static Map<String, Location> getLocationDictionary() { return locationDictionary; }

    public Player getPlayer() {
        return player;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public static Layout getLayout() {
        return layout;
    }

    public GameEngine() {
        loadJson("src/main/resources/skyrim.json");
        locationDictionary = Layout.generateLocationDictionary(layout);
        createPlayer();
        createGameStatus();
    }

    /**
     * Method that starts running the game.
     */
    public void runGame() {
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
    public static void loadJson(String filePath) {
        Gson gson = new Gson();
        try {
            Reader reader = new FileReader(filePath);
            layout = gson.fromJson(reader, Layout.class);
            checkJsonForNull(layout);
            checkJsonRooms(layout);
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
     * Creates a GameStatus with every field except for id.
     */
    public void createGameStatus() {
        gameStatus = new GameStatus();
        player.generateCommandOptions();

        gameStatus.setError(false);
        gameStatus.setMessage(player.getCurrentLocation().getDescription());
        gameStatus.setImageUrl(player.getCurrentLocation().getImageUrl());
        gameStatus.setVideoUrl(layout.getVideoUrl());
        gameStatus.setState(new AdventureState(player.getStringOfItemsInInventory()));
        gameStatus.setCommandOptions(player.getCommandOptions());
    }

    /**
     * Checks if Json has locations with directions leading to a valid location.
     * @param layout layout of class Layout
     */
    public static void checkJsonRooms(Layout layout) {
        Set<String> locationNames = new HashSet<>();
        for (Location location: layout.getLocations()) {
            locationNames.add(location.getName());
        }
        for (Location location: layout.getLocations()) {
            for (Direction direction: location.getDirections()) {
                if (!locationNames.contains(direction.getDirectionName())) {
                    throw new IllegalArgumentException("No location found");
                }
            }
        }
    }

    /**
     * Checks if anything in the Json file is null and throws NullPointerException() if there is
     * @param layout layout of class Layout
     */
    public static void checkJsonForNull(Layout layout) {
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

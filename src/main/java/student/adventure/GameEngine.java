package student.adventure;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;


public class GameEngine {
    private static Layout layout;
    public Map<String, Location> locationDictionary;
    public Player player;

    public static Layout getLayout() {
        return layout;
    }

    public Map<String, Location> getLocationDictionary() {
        return locationDictionary;
    }

    public Player getPlayer() {
        return player;
    }

    public GameEngine() {
        loadJson();
        createLocationDictionary();
        createPlayer();
    }

    /**
     * Method that starts running the game.
     */
    public void runGame() {
        checkJsonForNull();
        printGameIntro();

        System.out.println(player.getCurrentLocationInfo());

        String command;
        do {
            String[] arguments = UserInput.handleUserInput();
            command = arguments[0].toLowerCase();
            player.executeCommand(arguments);

            if (player.hasReachedEndingLocation()) {
                printGameEnding();
                return;
            }
        }
        while (!command.equals("quit") && !command.equals("exit"));
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
     * Prints the dialogue at the start of the game.
     */
    public static void printGameIntro() {
        System.out.println("Ralof: Hey, you. You’re finally awake. You were trying to cross the border, right?\n" +
                "Walked right into that Imperial ambush, same as us, and that thief over there.");
        System.out.println();

        System.out.println("Lokir: Damn you Stormcloaks. Skyrim was fine until you came along. Empire was nice and lazy.\n" +
                "If they hadn't been looking for you, I could’ve stolen that horse and been half way to Hammerfell.\n" +
                "You there. You and me — we should be here. It’s these Stormcloaks the Empire wants.");
        System.out.println();
    }

//    /**
//     * Prints current location's description, available directions, and items visible.
//     */
//    public static void printDefaultInfo() {
//        System.out.println(player.getCurrentLocation().getDescription());
//        System.out.println("From here, you can go: "
//                + Location.getFormattedStringOfAvailableDirections(player.getCurrentLocation()));
//        System.out.println("Items visible: " + Location.getStringOfAvailableItems(player.getCurrentLocation()));
//    }

    /**
     * Prints what happens when the game ends.
     */
    public static void printGameEnding() {
        System.out.println("You've made it to Windhelm; you win?");
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

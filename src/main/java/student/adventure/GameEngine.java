package student.adventure;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class GameEngine {
    public static Layout layout;
    public static Map<String, Location> locationDictionary;
    public static Player player;

    /**
     * Method that starts running the game.
     * @throws FileNotFoundException If JSON doesn't exist.
     */
    public static void runGame() throws FileNotFoundException, InterruptedException {
        loadJson();
        printGameIntro();

        locationDictionary = Layout.generateLocationDictionary(layout);
        player = new Player(locationDictionary.get(layout.getStartingLocation()), new ArrayList<>());

        printDefaultInfo();

        String command;
        do {
            String[] arguments = UserInput.handleUserInput();
            command = arguments[0].toLowerCase();
            player.executeCommand(arguments);

            if (Player.playerHasReachedEndingLocation(player)) {
                printGameEnding();
                break;
            }
        }
        while (!command.equals("quit") && !command.equals("exit"));
    }

    /**
     * Loads JSON into existence.
     * @throws FileNotFoundException If JSON doesn't exist.
     */
    public static void loadJson() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("src/main/resources/skyrim.json");
        layout = gson.fromJson(reader, Layout.class);
    }

    /**
     * Prints the dialogue at the start of the game.
     */
    public static void printGameIntro() throws InterruptedException {
        System.out.println("Ralof: Hey, you. You’re finally awake. You were trying to cross the border, right?\n" +
                "Walked right into that Imperial ambush, same as us, and that thief over there.");
        System.out.println();
        TimeUnit.SECONDS.sleep(2);

        System.out.println("Lokir: Damn you Stormcloaks. Skyrim was fine until you came along. Empire was nice and lazy.\n" +
                "If they hadn't been looking for you, I could’ve stolen that horse and been half way to Hammerfell.\n" +
                "You there. You and me — we should be here. It’s these Stormcloaks the Empire wants.");
        System.out.println();
        TimeUnit.SECONDS.sleep(2);
    }

    /**
     * Prints current location's description, available directions, and items visible.
     */
    public static void printDefaultInfo() {
        System.out.println(player.getCurrentLocation().getDescription());
        System.out.println("From here, you can go: "
                + Location.getFormattedStringOfAvailableDirections(player.getCurrentLocation()));
        System.out.println("Items visible: " + Location.getStringOfAvailableItems(player.getCurrentLocation()));
    }

    /**
     * Prints what happens when the game ends.
     */
    public static void printGameEnding() {
        System.out.println("You've made it to Windhelm; you win");
    }
}

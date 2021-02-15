package student.adventure;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.SQLOutput;
import java.util.*;


public class GameEngine {
    private static Layout layout;
    public static final Set<String> userCommands = new HashSet<>(
            Arrays.asList("quit", "exit", "go", "examine", "take", "drop"));
    public static Map<String, Location> locationDictionary;
    public static Player player;

    public static void runGame() throws FileNotFoundException {
        loadJson();
        printGameIntro();

        locationDictionary = Layout.generateLocationDictionary(layout);
        player = new Player(locationDictionary.get("Helgen"), new ArrayList<>());

        String command;
        do {
            System.out.println(player.getCurrentLocation().getDescription());
            System.out.println("From here, you can go: "
                    + Location.getFormattedStringOfAvailableDirections(player.getCurrentLocation()));
            System.out.println("Items visible: " + Location.getStringOfAvailableItems(player.getCurrentLocation()));

            String[] arguments = UserInput.handleUserInput();
            command = arguments[0];
            executeCommand(command, arguments);
            if (player.getCurrentLocation().getName().equals("Windhelm")) {
                // Ending location reached.
                break;
            }
        }
        while (!command.equals("quit") && !command.equals("exit"));
    }

    public static void loadJson() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("src/main/resources/skyrim.json");
        layout = gson.fromJson(reader, Layout.class);
    }

    public static void executeCommand(String command, String[] arguments) {
        switch(command) {
            case "go":
                if (arguments.length < 2) {
                    System.out.println("Enter a direction");
                } else {
                    player.goDirection(arguments[1]);
                }
                break;
            case "examine":
                System.out.println(player.getCurrentLocation().getDescription());
                break;
            case "take":
                if (arguments.length < 2) {
                    System.out.println("Enter an item to take");
                }
                break;
            case "drop":
                if (arguments.length < 2) {
                    System.out.println("Enter an item to drop");
                }
                break;
            default:
        }
    }

    public static void printGameIntro() {
        System.out.println("Ralof: Hey, you. You’re finally awake. You were trying to cross the border, right?\n" +
                "Walked right into that Imperial ambush, same as us, and that thief over there.");
        System.out.println();
        System.out.println("Lokir: Damn you Stormcloaks. Skyrim was fine until you came along. Empire was nice and lazy.\n" +
                "If they hadn't been looking for you, I could’ve stolen that horse and been half way to Hammerfell.\n" +
                "You there. You and me — we should be here. It’s these Stormcloaks the Empire wants.");
        System.out.println();
    }
}

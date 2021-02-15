package student.adventure;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;


public class GameEngine {
    private static Layout layout;
    public static final Set<String> userCommands = new HashSet<>(
            Arrays.asList("quit", "exit", "go", "examine", "take", "drop"));
    public static Map<String, Location> locationDictionary;
    public static Player player;

    public static void runGame() throws FileNotFoundException {
        loadJson();

        locationDictionary = Layout.generateLocationDictionary(layout);
        player = new Player(locationDictionary.get("Helgen"), new ArrayList<>());

        String command;
        do {
            String[] arguments = UserInput.handleUserInput();
            command = arguments[0];
            if (player.getCurrentLocation().getName().equals("Windhelm")) {
                // Ending location reached.
                break;
            }
            executeCommand(command, arguments);
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
}

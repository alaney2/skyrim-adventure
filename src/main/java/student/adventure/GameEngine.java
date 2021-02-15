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

    public static void runGame() throws FileNotFoundException {
        loadJson();
        Map<String, Location> locationMap = Layout.generateLocationMap(layout);
        Player player = new Player();
        player.setCurrentLocation(locationMap.get("Helgen"));
        String command;
        do {
            String[] arguments = getUserInput();
            command = arguments[0];
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

    public static String[] getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        String input = scanner.nextLine();
        String[] arguments = tokenizeString(input);
        while (arguments[0].length() == 0) {
            System.out.print("> ");
            input = scanner.nextLine();
            arguments = tokenizeString(input);
        }
        while (!userCommands.contains(arguments[0])) {
            String joinedArguments = String.join(" ", arguments);
            System.out.println("You don't understand \"" + joinedArguments + "\"!");
            System.out.print("> ");
            input = scanner.nextLine();
            arguments = tokenizeString(input);
        }

        return arguments;
    }

    public static String[] tokenizeString(String input) {
        input = input.toLowerCase();

        return input.split("\\s+");
    }
}

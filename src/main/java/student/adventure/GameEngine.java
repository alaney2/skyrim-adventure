package student.adventure;

//import com.sun.tools.jdeprscan.scan.Scan;

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
        while (!player.getCurrentLocation().getName().equals("Windhelm")) {
            String[] arguments = getUserInput();
            if (arguments[0].equals("quit") || arguments[0].equals("exit")) {
                break;
            }
        }
    }

    public static void loadJson() throws FileNotFoundException {
        Gson gson = new Gson();
        Reader reader = new FileReader("src/main/resources/skyrim.json");
        layout = gson.fromJson(reader, Layout.class);
    }

    public static String[] getUserInput() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] arguments = tokenizeString(input);
        while (arguments.length == 0) {
            input = scanner.nextLine();
            arguments = tokenizeString(input);
        }
        while (!userCommands.contains(arguments[0])) {
            input = String.join(" ", arguments);
            System.out.println("You don't understand " + input + "!");
            getUserInput();
        }
        return arguments;
    }

    public static String[] tokenizeString(String input) {
        input = input.toLowerCase();
        String[] arguments = input.split("\\s+");
        return arguments;
    }
}

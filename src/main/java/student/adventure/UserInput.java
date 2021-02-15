package student.adventure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserInput {
    public static final Set<String> userCommands = new HashSet<>(
            Arrays.asList("quit", "exit", "go", "examine", "take", "drop"));

    public static String[] handleUserInput() {
        String[] arguments = getArguments();

        while (arguments[0].length() == 0) {
            arguments = getArguments();
        }

        while (!userCommands.contains(arguments[0])) {
            String joinedArguments = String.join(" ", arguments);
            System.out.println("You don't understand \"" + joinedArguments + "\"!");
            arguments = getArguments();
        }

        return arguments;
    }

    public static String[] formatAndTokenizeString(String input) {
        input = input.toLowerCase();

        return input.split("\\s+");
    }

    public static String[] getArguments() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        String input = scanner.nextLine();

        return formatAndTokenizeString(input);
    }

    public static String capitalizeFirstLetter(String input) {
        input = input.toLowerCase();

        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}

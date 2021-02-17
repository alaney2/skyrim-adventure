package student.adventure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserInput {
    public static final Set<String> userCommands = new HashSet<>(
            Arrays.asList("quit", "exit", "go", "examine", "take", "drop", "inventory"));

    /**
     * Keeps requesting for input until it isn't whitespace and the first word is a command.
     * @return An array of String arguments split by whitespace with the first element being a command.
     */
    public static String[] handleUserInput() {
        String[] arguments = getTokenizedArguments();

        while (arguments[0].length() == 0) {
            arguments = getTokenizedArguments();
        }

        while (!userCommands.contains(arguments[0].toLowerCase())) {
            String joinedArguments = String.join(" ", arguments);
            System.out.println("You don't understand \"" + joinedArguments + "\"!");
            arguments = getTokenizedArguments();
        }

        return arguments;
    }

    /**
     * Takes in input and splits the String into an array of Strings separated by whitespace.
     * @return An array of String arguments split by whitespace.
     */
    public static String[] getTokenizedArguments() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        String input = scanner.nextLine();

        return input.split("\\s+");
    }

    /**
     * Capitalizes the first letter of a String.
     * @param input String to modify.
     * @return A String with the first letter capitalized and the rest lowercase.
     */
    public static String capitalizeFirstLetter(String input) {
        input = input.toLowerCase();

        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}

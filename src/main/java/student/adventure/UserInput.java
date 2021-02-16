package student.adventure;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UserInput {
    public static final Set<String> userCommands = new HashSet<>(
            Arrays.asList("quit", "exit", "go", "examine", "take", "drop"));

    /**
     *
     * @return
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
     *
     * @return
     */
    public static String[] getTokenizedArguments() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("> ");
        String input = scanner.nextLine();

        // return formatAndTokenizeString(input);

        return input.split("\\s+");
    }

    /**
     *
     * @param input
     * @return
     */
    public static String capitalizeFirstLetter(String input) {
        input = input.toLowerCase();

        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}

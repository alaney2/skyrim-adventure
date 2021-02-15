package student.adventure;

import java.util.Scanner;

public class UserInput {
    public static String[] handleUserInput() {
        String[] arguments = getArguments();

        while (arguments[0].length() == 0) {
            arguments = getArguments();
        }

        while (!GameEngine.userCommands.contains(arguments[0])) {
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

package student.adventure;

import java.util.Scanner;

public class UserInput {
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

        while (!GameEngine.userCommands.contains(arguments[0])) {
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

package student.adventure;

import java.util.Scanner;

public class GameEngine {
    public static void runGame() {
        Scanner scanner = new Scanner(System.in);
        String command = scanner.next();
        command = command.toLowerCase();
        while (!command.equals("quit") && !command.equals("exit")) {
            command = scanner.next();
        }
    }
}

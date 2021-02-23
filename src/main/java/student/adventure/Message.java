package student.adventure;

public class Message {
    /**
     * Prints the dialogue at the start of the game.
     */
    public static void printGameIntro() {
        System.out.println("Ralof: Hey, you. You’re finally awake. You were trying to cross the border, right?\n" +
                "Walked right into that Imperial ambush, same as us, and that thief over there.");
        System.out.println();

        System.out.println("Lokir: Damn you Stormcloaks. Skyrim was fine until you came along. Empire was nice and lazy.\n" +
                "If they hadn't been looking for you, I could’ve stolen that horse and been half way to Hammerfell.\n" +
                "You there. You and me — we should be here. It’s these Stormcloaks the Empire wants.");
        System.out.println();
    }

    /**
     * Prints what happens when the game ends.
     */
    public static void printGameEnding() {
        System.out.println("You've made it to Windhelm; you win?");
    }
}

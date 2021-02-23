package student.adventure;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;


import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class AdventureTest {
    // ByteArrayOutputStream and PrintStream are used to test console output.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    private static Gson gson;
    private static Reader reader;
    private static Layout layout;
    private static Player player;
    private static Map<String, Location> locationDictionary;
    GameEngine gameEngine;

    // Setting up tests.

    @Before
    public void setUp() throws FileNotFoundException {
        gson = new Gson();
        reader = new FileReader("src/main/resources/skyrim.json");
        layout = gson.fromJson(reader, Layout.class);
        locationDictionary = Layout.generateLocationDictionary(layout);
        gameEngine = new GameEngine();
        player = new Player(locationDictionary.get(layout.getStartingLocation()), new ArrayList<>());
        player.setEndingLocation(locationDictionary.get(layout.getEndingLocation()));
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    // Testing helper methods.

    @Test(expected = FileNotFoundException.class)
    public void loadInvalidFile() throws FileNotFoundException {
        gson = new Gson();
        reader = new FileReader("src/main/resources/dne.json");
        layout = gson.fromJson(reader, Layout.class);
    }

    @Test
    public void capitalizeFirstLetter() {
        assertEquals("String", UserInput.capitalizeFirstLetter("sTrInG"));
    }

    // Testing "go" command.

    @Test
    public void goNorth() {
        player.goDirection("North");
        assertEquals("Riverwood", player.getCurrentLocation().getName());
    }

    @Test
    public void goNorthWithExtraneousCommands() {
        player.executeCommand(new String[] {"go", "north", "south", "east", "west"});
        assertEquals("Riverwood", player.getCurrentLocation().getName());
    }

    @Test
    public void goWithoutArgument() {
        player.executeCommand(new String[] {"go"});
        String output = "Enter a direction to go\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void goNullDirection() {
        player.executeCommand(new String[] {"go", null});
        assertEquals("Helgen", player.getCurrentLocation().getName());
    }

    @Test
    public void goEastCaseInsensitive() {
        player.goDirection("eAsT");
        assertEquals("Falkreath", player.getCurrentLocation().getName());
    }

    @Test
    public void goInvalidDirection() {
        player.goDirection("southnorth");
        String output = "You can't go \"Southnorth\"!\n";
        assertEquals(output, outContent.toString());
    }

    // Testing "examine" command.
    @Test
    public void examine() {
        player.executeCommand(new String[] {"examine"});
        String output = "You are at Helgen, a moderately-sized community near Skyrim's South border.\n" +
                "From here, you can go: East, North, or West\n" +
                "Items visible: sword, GlassDoor\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void examineCaseInsensitive() {
        player.executeCommand(new String[] {"ExAmInE"});
        String output = "You are at Helgen, a moderately-sized community near Skyrim's South border.\n" +
                "From here, you can go: East, North, or West\n" +
                "Items visible: sword, GlassDoor\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void examineWithExtraneousCommands() {
        player.executeCommand(new String[] {"examine", "go", "random"});
        String output = "You are at Helgen, a moderately-sized community near Skyrim's South border.\n" +
                "From here, you can go: East, North, or West\n" +
                "Items visible: sword, GlassDoor\n";
        assertEquals(output, outContent.toString());
    }

    // Testing "take" command.

    @Test
    public void takeItem() {
        player.takeItem("sword");
        assertEquals("sword", player.getInventory().get(0).getItemName());
    }

    @Test
    public void takeNonexistentItem() {
        player.takeItem("nothing");
        assert(player.getInventory().size() == 0);
    }

    @Test
    public void takeItemCaseInsensitive() {
        player.takeItem("sWoRd");
        assertEquals("sword", player.getInventory().get(0).getItemName());
    }

    @Test
    public void takeItemWithoutArgument() {
        player.executeCommand(new String[] {"take"});
        String output = "Enter an item to take\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void takeValidItemOutputMessage() {
        player.takeItem("sword");
        String output = "Item \"sword\" taken.\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void takeInvalidItemOutputMessage() {
        player.takeItem("hay");
        String output = "There is no item \"hay\" at Helgen.\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void takeTwoOfTheSameItems() {
        player.getCurrentLocation().addItem(new Item("sword"));
        player.takeItem("sword");
        player.takeItem("sword");
        assert(player.getInventory().get(0).getItemName().equals("sword"));
        assert(player.getInventory().get(1).getItemName().equals("sword"));
    }

    @Test
    public void takeNullItem() {
        player.executeCommand(new String[] {"take", null});
    }

    // Testing "drop" command.

    @Test
    public void dropItem() {
        List<Item> inventory = new ArrayList<>();
        inventory.add(new Item("sword"));
        player.setInventory(inventory);
        player.dropItem("sword");
        assert(player.getCurrentLocation().getItems().get(0).getItemName().equals("sword"));
        assert(player.getCurrentLocation().getItems().get(1).getItemName().equals("GlassDoor"));
        assert(player.getCurrentLocation().getItems().get(2).getItemName().equals("sword"));
    }

    @Test
    public void dropValidItemOutputMessage() {
        List<Item> inventory = new ArrayList<>();
        inventory.add(new Item("sword"));
        player.setInventory(inventory);
        player.dropItem("sword");
        String output = "Item \"sword\" dropped.\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void dropInvalidItem() {
        player.dropItem("nothing");
        assert(player.getCurrentLocation().getItems().get(0).getItemName().equals("sword"));
        assert(player.getCurrentLocation().getItems().get(1).getItemName().equals("GlassDoor"));
    }

    @Test
    public void dropInvalidItemOutputMessage() {
        player.dropItem("hay");
        String output = "You don't have \"hay\"!\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void dropNullItem() {
        player.executeCommand(new String[] {"drop", null});
    }

    @Test
    public void dropItemWithoutArgument() {
        player.executeCommand(new String[] {"drop"});
        assert(player.getCurrentLocation().getItems().get(0).getItemName().equals("sword"));
        assert(player.getCurrentLocation().getItems().get(1).getItemName().equals("GlassDoor"));
    }

    @Test
    public void dropItemWithoutArgumentOutputMessage() {
        player.executeCommand(new String[] {"drop"});
        String output = "Enter an item to drop\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void dropTwoOfTheSameItems() {
        List<Item> inventory = new ArrayList<>(Arrays.asList(new Item("boots"), new Item("boots")));
        player.setInventory(inventory);
        player.dropItem("boots");
        assert(player.getCurrentLocation().getItems().get(2).getItemName().equals("boots"));
        player.dropItem("boots");
        assert(player.getCurrentLocation().getItems().get(3).getItemName().equals("boots"));
    }

    @Test
    public void inspectEmptyInventory() {
        player.executeCommand(new String[] {"inventory"});
        String output = "You have nothing in your inventory!\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void inspectEmptyInventoryCaseInsensitive() {
        player.executeCommand(new String[] {"InVeNToRY"});
        String output = "You have nothing in your inventory!\n";
        assertEquals(output, outContent.toString());
    }

    @Test
    public void inspectEmptyInventoryWithExtraneousCommands() {
        player.executeCommand(new String[] {"inventory", "go", "drop"});
        String output = "You have nothing in your inventory!\n";
        assertEquals(output, outContent.toString());
    }
}

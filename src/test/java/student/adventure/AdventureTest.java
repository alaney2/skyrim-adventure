package student.adventure;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;


import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;


public class AdventureTest {
    // ByteArrayOutputStream and PrintStream are used to test console output.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private static Gson gson;
    private static Reader reader;
    private static Layout layout;
    private static Player player;
    private static Map<String, Location> locationDictionary;

    @Before
    public void setUp() throws FileNotFoundException {
        gson = new Gson();
        reader = new FileReader("src/main/resources/skyrim.json");
        layout = gson.fromJson(reader, Layout.class);
        locationDictionary = Layout.generateLocationDictionary(layout);
        player = new Player(locationDictionary.get(layout.getStartingLocation()), new ArrayList<>());
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

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

//    @Test
//    public void examineCurrentLocation() {
//        player.executeCommand("examine", new String[] {"examine"});
//        String description = "You have arrived at Windhelm, the City of Kings.\n";
//        assertEquals(description, outContent.toString());
//    }
//
//    @Test
//    public void goWithoutArgument() {
//        player.executeCommand("go", new String[] {"go", "north"});
//        assertEquals("Riverwood", player.getCurrentLocation().getName());
//    }

    @Test
    public void goNullDirection() {

    }

    @Test
    public void goNorth() {

    }

    @Test
    public void goInvalidDirection() {

    }

    @Test
    public void dropItem() {

    }

    @Test
    public void takeItem() {

    }

    @Test
    public void takeNonexistentItem() {

    }

    @Test
    public void dropNonexistentItem() {

    }

    @Test
    public void dropItemWithoutArgument() {

    }

    @Test
    public void takeItemWithoutArgument() {

    }

}

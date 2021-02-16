package student.adventure;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;


import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;


public class AdventureTest {
    // ByteArrayOutputStream and PrintStream are used to test console output.
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    private static Gson gson;
    private static Reader reader;
    private static Layout layout;

    @Before
    public void setUp() throws FileNotFoundException {
        gson = new Gson();
        reader = new FileReader("src/main/resources/skyrim.json");
        layout = gson.fromJson(reader, Layout.class);
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
    public void invalidCommand() {

    }

    @Test
    public void examineCurrentLocation() {

    }

    @Test
    public void goWithoutArgument() {

    }

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

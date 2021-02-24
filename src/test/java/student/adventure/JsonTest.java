package student.adventure;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

public class JsonTest {
    Gson gson;
    FileReader reader;
    Layout layout;

    @Before
    public void setUp() {
        gson = new Gson();
    }

    @Test(expected = FileNotFoundException.class)
    public void testNonexistentJsonFile() throws FileNotFoundException {
        gson = new Gson();
        reader = new FileReader("");
    }

    @Test(expected = NullPointerException.class)
    public void jsonWithMissingFields() throws FileNotFoundException {
        reader = new FileReader("src/main/resources/siebel.json");
        layout = gson.fromJson(reader, Layout.class);
        GameEngine.checkJsonForNull(layout);
    }

    @Test(expected = NullPointerException.class)
    public void jsonWithNullFields() throws FileNotFoundException {
        reader = new FileReader("src/main/resources/siebel.json");
        layout = gson.fromJson(reader, Layout.class);
        GameEngine.checkJsonForNull(layout);
    }

    @Test
    public void jsonWithWrongFields() throws FileNotFoundException {
        reader = new FileReader("src/main/resources/wrongField.json");
        layout = gson.fromJson(reader, Layout.class);
        GameEngine.checkJsonForNull(layout);
    }

    @Test(expected = IllegalArgumentException.class)
    public void jsonWithNonexistentRooms() throws FileNotFoundException {
        reader = new FileReader("src/main/resources/nonexistentRoom.json");
        layout = gson.fromJson(reader, Layout.class);
        GameEngine.checkJsonForNull(layout);
        GameEngine.checkJsonRooms(layout);
    }
}

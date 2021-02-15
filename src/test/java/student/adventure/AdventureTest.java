package student.adventure;

import static org.junit.Assert.assertThat;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;


public class AdventureTest {
    Gson gson;
    Reader reader;
    Layout layout;

    @Before
    public void setUp() throws FileNotFoundException {
        gson = new Gson();
        reader = new FileReader("src/main/resources/skyrim.json");
        layout = gson.fromJson(reader, Layout.class);
    }

    @Test(expected = FileNotFoundException.class)
    public void loadInvalidFile() throws FileNotFoundException {
        gson = new Gson();
        reader = new FileReader("src/main/resources/dne.json");
        layout = gson.fromJson(reader, Layout.class);
    }

}
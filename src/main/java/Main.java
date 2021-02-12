import com.google.gson.Gson;
import student.Layout;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;


public class Main {
    private static Layout layout;

    public static void main(String[] args) throws FileNotFoundException {
        // Wishing you good luck on your Adventure!
        Gson gson = new Gson();
        Reader reader = new FileReader("src/main/resources/siebel.json");
        layout = gson.fromJson(reader, Layout.class);

    }
}

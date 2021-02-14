import com.google.gson.Gson;
import student.adventure.SkyrimMap;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;


public class Main {
    private static SkyrimMap skyrimMap;

    public static void main(String[] args) throws FileNotFoundException {
        // Wishing you good luck on your Adventure!
        Gson gson = new Gson();
        Reader reader = new FileReader("src/main/resources/skyrim.json");
        skyrimMap = gson.fromJson(reader, SkyrimMap.class);
        System.out.println(skyrimMap.getLocations().get(0).getName());
    }
}

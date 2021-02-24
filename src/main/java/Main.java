import org.glassfish.grizzly.http.server.HttpServer;
import student.adventure.GameEngine;
import student.adventure.Layout;
import student.server.AdventureResource;
import student.server.AdventureServer;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Main {
    /**
     * Starts the server and also runs game in console.
     * @param args default
     * @throws IOException for invalid json
     */
    public static void main(String[] args) throws IOException {
        HttpServer server = AdventureServer.createServer(AdventureResource.class);
        server.start();
        GameEngine gameEngine = new GameEngine();
        gameEngine.runGame();
    }
}

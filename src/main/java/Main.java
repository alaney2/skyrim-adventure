import org.glassfish.grizzly.http.server.HttpServer;
import student.adventure.GameEngine;
import student.server.AdventureResource;
import student.server.AdventureServer;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = AdventureServer.createServer(AdventureResource.class);
        server.start();
        GameEngine.runGame();
    }
}

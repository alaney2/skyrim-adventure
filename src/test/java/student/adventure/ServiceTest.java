package student.adventure;


import com.google.gson.Gson;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import student.server.Command;
import student.server.GameStatus;
import student.server.SkyrimAdventureService;

import javax.validation.constraints.AssertTrue;

import static org.junit.Assert.*;

public class ServiceTest {
    SkyrimAdventureService service;
    @Before
    public void setUp() {
        service = new SkyrimAdventureService();
    }

    @Test
    public void serviceReset() {
        service = new SkyrimAdventureService(3);
        service.reset();
        assertEquals(0, service.getId());
    }

    @Test
    public void newGame() {
        service.newGame();
        assertEquals(1, service.getId());
        assertEquals(1, service.getMapOfGamesRunning().size());

        service.newGame();
        assertEquals(2, service.getId());
        assertEquals(2, service.getMapOfGamesRunning().size());
    }

    @Test
    public void getExistingGame() {
        service.newGame();
        GameStatus gameStatus = service.getGame(0);
        assertFalse(gameStatus.getError());
    }

    @Test
    public void getNonexistentGame() {
        assertTrue(service.getGame(0).getError());
    }

    @Test
    public void destroyGame() {
        service.newGame();
        assertTrue(service.destroyGame(0));
        assertEquals(0, service.getMapOfGamesRunning().size());
    }

    @Test
    public void executeCommandGo() {
        service.newGame();
        service.executeCommand(0, new Command("go", "somewhere"));
    }

    @Test
    public void executeCommandTake() {
        service.newGame();
        service.executeCommand(0, new Command("take", "something"));
    }

    @Test
    public void executeCommandDrop() {
        service.newGame();
        service.executeCommand(0, new Command("drop", "something"));
    }
}

package me.smayya.iago;

import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testDefaultConstructor() {
        Game game = new Game(null, new EasyStrategy());
        assertEquals(new Board(), game.getBoard());
        assertFalse(game.isOver());
        assertNull(game.getWinner());
    }

    @Test
    public void testMoveGameOver() {
        String overBoard = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        Game game = new Game(new Board(overBoard), new EasyStrategy(), null);
        assertThrows(RuntimeException.class, () -> game.move(new Coordinate(0, 0), game.getCurrentPlayer()));
    }

    @Test
    public void testMovePlayerUsingAIMethod() {
        Game game = new Game(null, new EasyStrategy());
        assertThrows(RuntimeException.class, () -> game.move(game.getCurrentPlayer()));
    }

    @Test
    public void testAIMove() {
        Game game = new Game(new EasyStrategy(), null);
        game.move(Player.BLACK);
        String expected = "..........................xxx......xo...........................";
        assertEquals(expected, game.getBoard().getBoard());
    }

    @Test
    public void testWinner() {
        String overBoard = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
        Game game = new Game(new Board(overBoard), new EasyStrategy(), null);
        assertEquals(Player.BLACK, game.getWinner());
    }

    @Test
    public void testTie() {
        String overBoard = "xoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxoxo";
        Game game = new Game(new Board(overBoard), new EasyStrategy(), null);
        assertNull(game.getWinner());
    }

    @Test
    public void testGetPlayers() {
        Game game = new Game(null, new EasyStrategy());
        Map<Player, Strategy> expected = new HashMap<>();
        expected.put(Player.BLACK, null);
        expected.put(Player.WHITE, new EasyStrategy());
        assertEquals(expected, game.getPlayers());
    }

    @Test
    public void testGetPlayerStrategy() {
        Game game = new Game(null, new EasyStrategy());
        assertEquals(new EasyStrategy(), game.getPlayerStrategy(Player.WHITE));
    }
}

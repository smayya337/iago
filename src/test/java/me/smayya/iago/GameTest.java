package me.smayya.iago;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testDefaultConstructor() {
        Game game = new Game(null, new EasyStrategy());
        assertEquals(new Board(), game.getBoard());
        assertFalse(game.isOver());
        assertNull(game.getWinner());
    }
}

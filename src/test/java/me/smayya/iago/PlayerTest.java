package me.smayya.iago;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testBlackOpponent() {
        assertEquals(Player.WHITE, Player.getOpponent(Player.BLACK));
    }

    @Test
    public void testWhiteOpponent() {
        assertEquals(Player.BLACK, Player.getOpponent(Player.WHITE));
    }
}

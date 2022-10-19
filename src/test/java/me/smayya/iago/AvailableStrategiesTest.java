package me.smayya.iago;

import org.junit.jupiter.api.*;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class AvailableStrategiesTest {

    @Test
    public void testGetStrategyByName() {
        assertInstanceOf(AvailableStrategies.class, AvailableStrategies.getStrategyByName("EASY"));
    }

    @Test
    public void testGetStrategyByNameNonexistent() {
        assertThrows(IllegalArgumentException.class, () -> AvailableStrategies.getStrategyByName("NONEXISTENT"));
    }

    @Test
    public void testGetStrategy() {
        assertInstanceOf(EasyStrategy.class, AvailableStrategies.EASY.getStrategy());
    }
}

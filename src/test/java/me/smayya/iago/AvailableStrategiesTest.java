package me.smayya.iago;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class AvailableStrategiesTest {

    @Test
    public void testGetStrategyByName() {
        assertInstanceOf(AvailableStrategies.class, AvailableStrategies.getStrategyByName("BASIC"));
    }

    @Test
    public void testGetStrategyByNameNonexistent() {
        assertThrows(IllegalArgumentException.class, () -> AvailableStrategies.getStrategyByName("NONEXISTENT"));
    }

    @Test
    public void testGetStrategy() {
        assertInstanceOf(EasyStrategy.class, AvailableStrategies.BASIC.getStrategy());
    }
}

package me.smayya.iago;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CoordinateTest {
    @Test
    public void checkCoordinateEquality() {
        Coordinate coordinate1 = new Coordinate(0, 0);
        Coordinate coordinate2 = new Coordinate(0, 0);
        assertEquals(coordinate1, coordinate2);
    }
}

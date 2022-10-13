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

    @Test
    public void checkCoordinateSameHash() {
        Coordinate coordinate1 = new Coordinate(0, 0);
        Coordinate coordinate2 = new Coordinate(0, 0);
        assertEquals(coordinate1.hashCode(), coordinate2.hashCode());
    }

    @Test
    public void testCoordinateToIndex() {
        int coordinateRow = 4;
        int coordinateColumn = 4;
        Coordinate coordinate = new Coordinate(coordinateRow, coordinateColumn);
        int boardRows = 8;
        int expectedIndex = coordinateRow * boardRows + coordinateColumn;
        assertEquals(expectedIndex, Coordinate.getIndexFromCoordinate(coordinate, boardRows));
    }

    @Test
    public void testIndexToCoordinate() {
        int coordinateRow = 4;
        int coordinateColumn = 4;
        Coordinate coordinate1 = new Coordinate(coordinateRow, coordinateColumn);
        int boardRows = 8;
        int index = coordinateRow * boardRows + coordinateColumn;
        Coordinate coordinate2 = Coordinate.getCoordinateFromIndex(index, boardRows);
        assertEquals(coordinate1, coordinate2);
    }

    @Test
    public void testUp() {
        Coordinate coordinate1 = new Coordinate(3, 4);
        Coordinate coordinate2 = new Coordinate(4, 4);
        assertTrue(coordinate1.isUp(coordinate2));
    }
}

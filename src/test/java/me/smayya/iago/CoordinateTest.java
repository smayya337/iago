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

    @Test
    public void testDown() {
        Coordinate coordinate1 = new Coordinate(4, 4);
        Coordinate coordinate2 = new Coordinate(3, 4);
        assertTrue(coordinate1.isDown(coordinate2));
    }

    @Test
    public void testLeft() {
        Coordinate coordinate1 = new Coordinate(4, 3);
        Coordinate coordinate2 = new Coordinate(4, 4);
        assertTrue(coordinate1.isLeft(coordinate2));
    }

    @Test
    public void testRight() {
        Coordinate coordinate1 = new Coordinate(4, 4);
        Coordinate coordinate2 = new Coordinate(4, 3);
        assertTrue(coordinate1.isRight(coordinate2));
    }

    @Test
    public void testUpLeft() {
        Coordinate coordinate1 = new Coordinate(3, 3);
        Coordinate coordinate2 = new Coordinate(4, 4);
        assertFalse(coordinate1.isUp(coordinate2));
        assertFalse(coordinate1.isLeft(coordinate2));
        assertTrue(coordinate1.isUpLeft(coordinate2));
    }

    @Test
    public void testUpRight() {
        Coordinate coordinate1 = new Coordinate(3, 5);
        Coordinate coordinate2 = new Coordinate(4, 4);
        assertFalse(coordinate1.isUp(coordinate2));
        assertFalse(coordinate1.isRight(coordinate2));
        assertTrue(coordinate1.isUpRight(coordinate2));
    }

    @Test
    public void testDownLeft() {
        Coordinate coordinate1 = new Coordinate(5, 3);
        Coordinate coordinate2 = new Coordinate(4, 4);
        assertFalse(coordinate1.isDown(coordinate2));
        assertFalse(coordinate1.isLeft(coordinate2));
        assertTrue(coordinate1.isDownLeft(coordinate2));
    }

    @Test
    public void testDownRight() {
        Coordinate coordinate1 = new Coordinate(5, 5);
        Coordinate coordinate2 = new Coordinate(4, 4);
        assertFalse(coordinate1.isDown(coordinate2));
        assertFalse(coordinate1.isRight(coordinate2));
        assertTrue(coordinate1.isDownRight(coordinate2));
    }
}

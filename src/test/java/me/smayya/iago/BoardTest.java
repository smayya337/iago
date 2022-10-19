package me.smayya.iago;

import org.junit.jupiter.api.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    Board board;
    @BeforeEach
    public void createDefaultBoard() {
        board = new Board();
    }
    @Test
    public void testNoArgConstructor() {
        String expected = "...........................ox......xo...........................";
        assertEquals(expected, board.getBoard());
    }

    @Test
    public void testCloneEquals() {
        Board newBoard = board.clone();
        assertEquals(board, newBoard);
    }

    @Test
    public void testHashCode() {
        Board newBoard = new Board();
        assertEquals(board.hashCode(), newBoard.hashCode());
    }

    @Test
    public void testFlip() {
        Coordinate coordinate = new Coordinate(2, 3);
        board.move(coordinate, Player.BLACK);
        String expected = "...................x.......xx......xo...........................";
        assertEquals(expected, board.getBoard());
    }

    @Test
    public void testValidLocations() {
        String midGameBoard = "............o....ooo....oxoxx....xooo....x.x....................";
        board = new Board(midGameBoard);
        Set<Coordinate> validLocations = board.getValidLocations(Player.WHITE);
        Set<Coordinate> expected = new HashSet<>();
        expected.add(new Coordinate(2, 0));
        expected.add(new Coordinate(4, 0));
        expected.add(new Coordinate(5, 0));
        expected.add(new Coordinate(6, 0));
        expected.add(new Coordinate(6, 1));
        expected.add(new Coordinate(5, 2));
        expected.add(new Coordinate(6, 2));
        expected.add(new Coordinate(6, 3));
        expected.add(new Coordinate(2, 4));
        expected.add(new Coordinate(6, 4));
        expected.add(new Coordinate(2, 5));
        expected.add(new Coordinate(3, 5));
        expected.add(new Coordinate(4, 5));
        for (Coordinate location : validLocations) {
            assertTrue(expected.contains(location));
        }
        assertEquals(expected.size(), validLocations.size());
    }

    @Test
    public void testIsCorner() {
        int corner = 63;
        assertTrue(board.isCorner(corner));
        assertFalse(board.isEdge(corner));
    }

    @Test
    public void testIsEdge() {
        int edge = 62;
        assertFalse(board.isCorner(edge));
        assertTrue(board.isEdge(edge));
    }

    @Test
    public void testIllegalBoardSize() {
        String illegalBoard = "....";
        assertThrows(IllegalArgumentException.class, () -> new Board(illegalBoard));
    }

    @Test
    public void testCounts() {
        assertEquals(2, board.getCount(Player.BLACK));
        assertEquals(2, board.getCount(Player.WHITE));
    }
}

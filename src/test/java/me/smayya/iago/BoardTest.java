package me.smayya.iago;

import org.junit.jupiter.api.*;

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
        Coordinate coordinate = new Coordinate(3, 4);
        board.move(coordinate, Player.BLACK);
        String expected = "...................x.......xx......xo...........................";
        assertEquals(expected, board.getBoard());
    }
}

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
}

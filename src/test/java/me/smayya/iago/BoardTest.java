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
        int sideLength = board.getSideLength();
        assertEquals(Board.DEFAULT_SIDE_LENGTH, sideLength);
        int size = sideLength * sideLength;
        assertEquals(size, board.getSize());
        // TODO: add more stuff - make methods/fields protected?
    }

    @Test
    public void testCloneEquals() {
        Board newBoard = board.clone();
        assertEquals(board, newBoard);
    }
}

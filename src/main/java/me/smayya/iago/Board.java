package me.smayya.iago;

import java.util.HashSet;
import java.util.Set;

public class Board {
    private static final String EMPTY_CHARACTER = ".";
    private static final int DEFAULT_ROWS = 8;
    private static final int DEFAULT_COLUMNS = 8;
    private final int rows;
    private final int columns;
    private final int size;
    private final String board;

    public Board(int rows, int columns, String board) {
        this.rows = rows;
        this.columns = columns;
        this.size = rows * columns;
        this.board = board;
    }

    public Board(int rows, int columns) {
        this(rows, columns, createEmptyBoard(rows, columns));
    }

    public Board() {
        this(DEFAULT_ROWS, DEFAULT_COLUMNS);
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getSize() {
        return size;
    }

    public String getBoard() {
        return board;
    }

    private static String createEmptyBoard(int rows, int columns) {
        int size = rows * columns;
        String board = "";
        for (int i = 0; i < size; i++) {
            board += EMPTY_CHARACTER;
        }
        return board;
    }

    public Set<Coordinate> getEmptyCoordinates() {
        Set<Coordinate> emptyCoordinates = new HashSet<>();
        for (int i = 0; i < size; i++) {
            if (board.charAt(i) == EMPTY_CHARACTER.charAt(0)) {
                emptyCoordinates.add(Coordinate.getCoordinateFromIndex(i, rows));
            }
        }
        return emptyCoordinates;
    }
}

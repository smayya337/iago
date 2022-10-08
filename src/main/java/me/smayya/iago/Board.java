package me.smayya.iago;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Board {
    private static final String EMPTY_CHARACTER = ".";
    private static final int DEFAULT_ROWS = 8;
    private static final int DEFAULT_COLUMNS = 8;
    private final int rows;
    private final int columns;
    private final int size;
    private String board;
    private final Map<String, Integer> counts;

    public Board(int rows, int columns, String board) {
        this.rows = rows;
        this.columns = columns;
        this.size = rows * columns;
        this.board = board;
        this.counts = initializeCounts(board);
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

    public Set<Coordinate> getValidLocations(Player player) {
        return getEmptyCoordinates().stream().filter(x -> isValid(x, player)).collect(Collectors.toSet());
    }

    private boolean isValid(Coordinate coordinate, Player player) {
        return !flippedSpots(coordinate, player).isEmpty();
    }

    private Set<Coordinate> flippedSpots(Coordinate coordinate, Player player) {
        return new HashSet<>();
    }

    public void move(Coordinate coordinate, Player player) {
        Set<Coordinate> spotsToFlip = flippedSpots(coordinate, player);
        flip(coordinate, player);
        for (Coordinate spot:
             spotsToFlip) {
            flip(spot, player);
        }
    }

    private void flip(Coordinate coordinate, Player player) {
        int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
        String originalToken = String.valueOf(board.charAt(index));
        replaceBoard(index, player);
        updateCounts(originalToken, -1);
        updateCounts(player.getToken(), 1);
    }

    private void replaceBoard(int index, Player player) {
        board = board.substring(0, index) + player.getToken() + board.substring(index + 1);
    }

    private void updateCounts(String key, int difference) {
        counts.put(key, counts.getOrDefault(key, 0) + difference);
    }

    private static Map<String, Integer> initializeCounts(String board) {
        Map<String, Integer> counts = new HashMap<>();
        for (char c:
             board.toCharArray()) {
            String cString = String.valueOf(c);
            counts.put(cString, counts.getOrDefault(cString, 0) + 1);
        }
        return counts;
    }
}

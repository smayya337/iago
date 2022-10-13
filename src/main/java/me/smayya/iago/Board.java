package me.smayya.iago;

import java.util.*;
import java.util.stream.Collectors;

public class Board implements Cloneable {
    private static final String EMPTY_CHARACTER = ".";
    private static final int DEFAULT_SIDE_LENGTH = 8;
    private final int sideLength;
    private final int size;
    private final Map<String, Integer> counts;
    private final Set<Coordinate> coordinates;
    private final Map<Direction, List<Coordinate>[]> relationships;
    private String board;

    private Board(int sideLength, String board, int size, Map<String, Integer> counts, Set<Coordinate> coordinates, Map<Direction, List<Coordinate>[]> relationships) {
        this.sideLength = sideLength;
        this.size = size;
        this.board = board;
        this.counts = counts;
        this.coordinates = coordinates;
        this.relationships = relationships;
    }

    public Board(int sideLength, String board) {
        checkSideLength(sideLength);
        this.sideLength = sideLength;
        this.size = sideLength * sideLength;
        this.board = board;
        this.counts = initializeCounts(board);
        this.coordinates = initializeCoordinates(sideLength);
        this.relationships = BoardRelationships.initializeRelationships(coordinates, sideLength);
    }

    public Board(int sideLength) {
        this(sideLength, createEmptyBoard(sideLength, Player.BLACK, Player.WHITE));
    }

    public Board() {
        this(DEFAULT_SIDE_LENGTH);
    }

    private static String createEmptyBoard(int sideLength, Player player1, Player player2) {
        int size = sideLength * sideLength;
        ArrayList<Integer> populatedIndices = getPopulatedSpaces(sideLength);
        String board = "";
        for (int i = 0; i < size; i++) {
            if (populatedIndices.contains(i)) {
                Coordinate coordinate = Coordinate.getCoordinateFromIndex(i, sideLength);
                if (coordinate.getRow() == coordinate.getColumn()) {
                    board += player1.getToken();
                } else {
                    board += player2.getToken();
                }
            } else {
                board += EMPTY_CHARACTER;
            }
        }
        return board;
    }

    private static ArrayList<Integer> getPopulatedSpaces(int sideLength) {
        int middle = sideLength / 2;
        ArrayList<Integer> populatedIndices = new ArrayList<>();
        for (int i = middle - 1; i <= middle; i++) {
            for (int j = middle - 1; j <= middle; j++) {
                populatedIndices.add(Coordinate.getIndexFromCoordinate(new Coordinate(i, j), sideLength));
            }
        }
        return populatedIndices;
    }

    private static Map<String, Integer> initializeCounts(String board) {
        Map<String, Integer> counts = new HashMap<>();
        for (char c :
                board.toCharArray()) {
            String cString = String.valueOf(c);
            counts.put(cString, counts.getOrDefault(cString, 0) + 1);
        }
        return counts;
    }

    private static Set<Coordinate> initializeCoordinates(int sideLength) {
        Set<Coordinate> coordinates = new HashSet<>();
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                coordinates.add(new Coordinate(i, j));
            }
        }
        return coordinates;
    }

    private void checkSideLength(int sideLength) {
        checkSideLengthIsEven(sideLength);
        checkSideLengthIsAtLeastFour(sideLength);
    }

    private void checkSideLengthIsEven(int sideLength) {
        if (sideLength % 2 == 1) {
            throw new IllegalArgumentException("Cannot create board with an odd side length!");
        }
    }

    private void checkSideLengthIsAtLeastFour(int sideLength) {
        if (sideLength < 4) {
            throw new IllegalArgumentException("Cannot create board with a side length of less than 4!");
        }
    }

    public int getSideLength() {
        return sideLength;
    }

    public int getSize() {
        return size;
    }

    public String getBoard() {
        return board;
    }

    public Set<Coordinate> getEmptyCoordinates() {
        return coordinates.stream().filter(this::isEmpty).collect(Collectors.toSet());
    }

    private boolean isEmpty(Coordinate coordinate) {
        int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
        String value = String.valueOf(board.charAt(index));
        return value.equals(EMPTY_CHARACTER);
    }

    public Set<Coordinate> getValidLocations(Player player) {
        return getEmptyCoordinates().stream().filter(x -> isValid(x, player)).collect(Collectors.toSet());
    }

    private boolean isValid(Coordinate coordinate, Player player) {
        return !flippedSpots(coordinate, player).isEmpty();
    }

    private Set<Coordinate> flippedSpots(Coordinate coordinate, Player player) {
        int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
        Set<Coordinate> spots = new HashSet<>();
        for (Direction direction :
                relationships.keySet()) {
            List<Coordinate> potentialSpots = relationships.get(direction)[index];
            spots.addAll(flippedSpotsInList(potentialSpots, player));
        }
        return spots;
    }

    private Set<Coordinate> flippedSpotsInList(List<Coordinate> spotList, Player player) {
        boolean terminatedBySamePlayerToken = false;
        Set<Coordinate> spots = new HashSet<>();
        for (Coordinate spot :
                spotList) {
            String token = getTokenAtCoordinate(spot);
            if (token.equals(player.getToken())) {
                terminatedBySamePlayerToken = true;
                break;
            } else if (token.equals(EMPTY_CHARACTER)) {
                break;
            } else {
                spots.add(spot);
            }
        }
        if (terminatedBySamePlayerToken) {
            return spots;
        } else {
            return new HashSet<>();
        }
    }

    public void move(Coordinate coordinate, Player player) {
        Set<Coordinate> spotsToFlip = flippedSpots(coordinate, player);
        flip(coordinate, player);
        for (Coordinate spot :
                spotsToFlip) {
            flip(spot, player);
        }
    }

    private void flip(Coordinate coordinate, Player player) {
        int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
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

    public String getTokenAtCoordinate(Coordinate coordinate) {
        int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
        return String.valueOf(board.charAt(index));
    }

    @Override
    public Board clone() {
        return new Board(sideLength, board, size, counts, coordinates, relationships);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board1 = (Board) o;
        return Objects.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(board);
    }

    public int getCount(Player player) {
        return counts.get(player.getToken());
    }
}

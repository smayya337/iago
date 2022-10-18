package me.smayya.iago;

import java.util.*;
import java.util.stream.Collectors;

public class Board implements Cloneable {
    protected static final String EMPTY_CHARACTER = ".";
    protected static final int SIDE_LENGTH = 8;
    protected static final int SIZE = SIDE_LENGTH * SIDE_LENGTH;
    private final BoardCounts counts;
    private final Set<Coordinate> coordinates;
    private final Map<Direction, List<Coordinate>[]> relationships;
    private String board;

    private Board(String board, BoardCounts counts, Set<Coordinate> coordinates, Map<Direction, List<Coordinate>[]> relationships) {
        this.board = board;
        this.counts = counts;
        this.coordinates = coordinates;
        this.relationships = relationships;
    }

    public Board(String board) {
        checkSideLength(board);
        this.board = board;
        this.counts = new BoardCounts(board);
        this.coordinates = initializeCoordinates();
        this.relationships = BoardRelationships.initializeRelationships(coordinates, SIDE_LENGTH);
    }

    public Board() {
        this(createEmptyBoard());
    }

    private static String createEmptyBoard() {
        ArrayList<Integer> populatedIndices = getPopulatedSpaces();
        String board = "";
        for (int i = 0; i < SIZE; i++) {
            if (populatedIndices.contains(i)) {
                Coordinate coordinate = Coordinate.getCoordinateFromIndex(i, SIDE_LENGTH);
                if (coordinate.getRow() == coordinate.getColumn()) {
                    board += Player.BLACK.getToken();
                } else {
                    board += Player.WHITE.getToken();
                }
            } else {
                board += EMPTY_CHARACTER;
            }
        }
        return board;
    }

    private static ArrayList<Integer> getPopulatedSpaces() {
        int middle = SIDE_LENGTH / 2;
        ArrayList<Integer> populatedIndices = new ArrayList<>();
        for (int i = middle - 1; i <= middle; i++) {
            for (int j = middle - 1; j <= middle; j++) {
                populatedIndices.add(Coordinate.getIndexFromCoordinate(new Coordinate(i, j), SIDE_LENGTH));
            }
        }
        return populatedIndices;
    }

    private static Set<Coordinate> initializeCoordinates() {
        Set<Coordinate> coordinates = new HashSet<>();
        for (int i = 0; i < Board.SIDE_LENGTH; i++) {
            for (int j = 0; j < Board.SIDE_LENGTH; j++) {
                coordinates.add(new Coordinate(i, j));
            }
        }
        return coordinates;
    }

    private void checkSideLength(String board) {
        if (board.length() != SIZE) {
            throw new IllegalArgumentException("Board must be " + SIDE_LENGTH + "x" + SIDE_LENGTH + "!");
        }
    }

    public String getBoard() {
        return board;
    }

    public Set<Coordinate> getEmptyCoordinates() {
        return coordinates.stream().filter(this::isEmpty).collect(Collectors.toSet());
    }

    private boolean isEmpty(Coordinate coordinate) {
        int index = Coordinate.getIndexFromCoordinate(coordinate, SIDE_LENGTH);
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
        int index = Coordinate.getIndexFromCoordinate(coordinate, SIDE_LENGTH);
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
        int index = Coordinate.getIndexFromCoordinate(coordinate, SIDE_LENGTH);
        String originalToken = String.valueOf(board.charAt(index));
        replaceBoard(index, player);
        counts.update(originalToken, -1);
        counts.update(player.getToken(), 1);
    }

    private void replaceBoard(int index, Player player) {
        board = board.substring(0, index) + player.getToken() + board.substring(index + 1);
    }

    public String getTokenAtCoordinate(Coordinate coordinate) {
        int index = Coordinate.getIndexFromCoordinate(coordinate, SIDE_LENGTH);
        return String.valueOf(board.charAt(index));
    }

    @Override
    public Board clone() {
        return new Board(board, counts, coordinates, relationships);
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

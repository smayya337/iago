package me.smayya.iago;

import java.util.*;
import java.util.stream.Collectors;

public class Board {
    private static final String EMPTY_CHARACTER = ".";
    private static final int DEFAULT_ROWS = 8;
    private static final int DEFAULT_COLUMNS = 8;
    private static final String[] DIRECTIONS = {"up", "down", "left", "right", "upleft", "upright", "downleft", "downright"};
    private final int rows;
    private final int columns;
    private final int size;
    private String board;
    private final Map<String, Integer> counts;
    private final Set<Coordinate> coordinates;
    private final Map<String, List<Coordinate>[]> relationships;

    public Board(int rows, int columns, String board) {
        this.rows = rows;
        this.columns = columns;
        this.size = rows * columns;
        this.board = board;
        this.counts = initializeCounts(board);
        this.coordinates = initializeCoordinates(rows, columns);
        this.relationships = initializeRelationships(coordinates);
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
        return coordinates.stream().filter(this::isEmpty).collect(Collectors.toSet());
    }

    private boolean isEmpty(Coordinate coordinate) {
        int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
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
        int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
        Set<Coordinate> spots = new HashSet<>();
        for (String direction :
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
        for (char c :
                board.toCharArray()) {
            String cString = String.valueOf(c);
            counts.put(cString, counts.getOrDefault(cString, 0) + 1);
        }
        return counts;
    }

    private static Set<Coordinate> initializeCoordinates(int rows, int columns) {
        Set<Coordinate> coordinates = new HashSet<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                coordinates.add(new Coordinate(i, j));
            }
        }
        return coordinates;
    }

    private Map<String, List<Coordinate>[]> initializeRelationships(Set<Coordinate> coordinates) {
        Map<String, List<Coordinate>[]> relationships = new HashMap<>();
        ArrayList<List<Coordinate>[]> relationsArrayList = new ArrayList<>();
        relationsArrayList.add(initializeUps(coordinates, rows));
        relationsArrayList.add(initializeDowns(coordinates, rows));
        relationsArrayList.add(initializeLefts(coordinates, rows));
        relationsArrayList.add(initializeRights(coordinates, rows));
        relationsArrayList.add(initializeUpLefts(coordinates, rows));
        relationsArrayList.add(initializeUpRights(coordinates, rows));
        relationsArrayList.add(initializeDownLefts(coordinates, rows));
        relationsArrayList.add(initializeDownRights(coordinates, rows));
        for (int i = 0; i < relationsArrayList.size(); i++) {
            relationships.put(DIRECTIONS[i], relationsArrayList.get(i));
        }
        return relationships;
    }

    private static List<Coordinate>[] initializeUps(Set<Coordinate> coordinates, int rows) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate :
                coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
            relations[index] = coordinates.stream().filter(coordinate::isUp).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeDowns(Set<Coordinate> coordinates, int rows) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate :
                coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
            relations[index] = coordinates.stream().filter(coordinate::isDown).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeLefts(Set<Coordinate> coordinates, int rows) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate :
                coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
            relations[index] = coordinates.stream().filter(coordinate::isLeft).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeRights(Set<Coordinate> coordinates, int rows) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate :
                coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
            relations[index] = coordinates.stream().filter(coordinate::isRight).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeUpLefts(Set<Coordinate> coordinates, int rows) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate :
                coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
            relations[index] = coordinates.stream().filter(coordinate::isUpLeft).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeUpRights(Set<Coordinate> coordinates, int rows) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate :
                coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
            relations[index] = coordinates.stream().filter(coordinate::isUpRight).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeDownLefts(Set<Coordinate> coordinates, int rows) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate :
                coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
            relations[index] = coordinates.stream().filter(coordinate::isDownLeft).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeDownRights(Set<Coordinate> coordinates, int rows) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate :
                coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
            relations[index] = coordinates.stream().filter(coordinate::isDownRight).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    public String getTokenAtCoordinate(Coordinate coordinate) {
        int index = Coordinate.getIndexFromCoordinate(coordinate, rows);
        return String.valueOf(board.charAt(index));
    }
}

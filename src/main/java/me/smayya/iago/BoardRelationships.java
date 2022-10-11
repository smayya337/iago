package me.smayya.iago;

import java.util.*;
import java.util.stream.Collectors;

public class BoardRelationships {
    private static List<Coordinate>[] initializeUps(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(coordinate::isUp).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeDowns(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(coordinate::isDown).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeLefts(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(coordinate::isLeft).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeRights(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(coordinate::isRight).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeUpLefts(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(coordinate::isUpLeft).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeUpRights(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(coordinate::isUpRight).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeDownLefts(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(coordinate::isDownLeft).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeDownRights(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(coordinate::isDownRight).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    public static Map<String, List<Coordinate>[]> initializeRelationships(Set<Coordinate> coordinates, int sideLength) {
        Map<String, List<Coordinate>[]> relationships = new HashMap<>();
        ArrayList<List<Coordinate>[]> relationsArrayList = new ArrayList<>();
        relationsArrayList.add(initializeUps(coordinates, sideLength));
        relationsArrayList.add(initializeDowns(coordinates, sideLength));
        relationsArrayList.add(initializeLefts(coordinates, sideLength));
        relationsArrayList.add(initializeRights(coordinates, sideLength));
        relationsArrayList.add(initializeUpLefts(coordinates, sideLength));
        relationsArrayList.add(initializeUpRights(coordinates, sideLength));
        relationsArrayList.add(initializeDownLefts(coordinates, sideLength));
        relationsArrayList.add(initializeDownRights(coordinates, sideLength));
        for (int i = 0; i < relationsArrayList.size(); i++) {
            relationships.put(Board.DIRECTIONS[i], relationsArrayList.get(i));
        }
        return relationships;
    }
}

package me.smayya.iago;

import java.util.*;
import java.util.stream.Collectors;

public class BoardRelationships {
    private static List<Coordinate>[] initializeUps(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(x -> x.isUp(coordinate)).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeDowns(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(x -> x.isDown(coordinate)).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeLefts(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(x -> x.isLeft(coordinate)).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeRights(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(x -> x.isRight(coordinate)).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeUpLefts(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(x -> x.isUpLeft(coordinate)).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
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
            relations[index] = coordinates.stream().filter(x -> x.isDownLeft(coordinate)).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    private static List<Coordinate>[] initializeDownRights(Set<Coordinate> coordinates, int sideLength) {
        List<Coordinate>[] relations = new List[coordinates.size()];
        for (Coordinate coordinate : coordinates) {
            int index = Coordinate.getIndexFromCoordinate(coordinate, sideLength);
            relations[index] = coordinates.stream().filter(x -> x.isDownRight(coordinate)).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        }
        return relations;
    }

    public static Map<Direction, List<Coordinate>[]> initializeRelationships(Set<Coordinate> coordinates, int sideLength) {
        Map<Direction, List<Coordinate>[]> relationships = new HashMap<>();
        ArrayList<List<Coordinate>[]> relationsArrayList = new ArrayList<>();
        relationsArrayList.add(initializeUps(coordinates, sideLength));
        relationsArrayList.add(initializeDowns(coordinates, sideLength));
        relationsArrayList.add(initializeLefts(coordinates, sideLength));
        relationsArrayList.add(initializeRights(coordinates, sideLength));
        relationsArrayList.add(initializeUpLefts(coordinates, sideLength));
        relationsArrayList.add(initializeUpRights(coordinates, sideLength));
        relationsArrayList.add(initializeDownLefts(coordinates, sideLength));
        relationsArrayList.add(initializeDownRights(coordinates, sideLength));
        Direction[] directions = Direction.values();
        for (int i = 0; i < relationsArrayList.size(); i++) {
            relationships.put(directions[i], relationsArrayList.get(i));
        }
        return relationships;
    }
}

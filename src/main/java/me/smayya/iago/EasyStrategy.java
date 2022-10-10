package me.smayya.iago;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class EasyStrategy extends Strategy {
    private static final double CORNER_POINTS = 4;
    private static final double EDGE_POINTS = 2;
    private  static final double NORMAL_POINTS = 1;
    @Override
    public Coordinate getMove(Board board, Player player) {
        Map<Coordinate, Double> scores = new HashMap<>();
        for (Coordinate coordinate:
             board.getValidLocations(player)) {
            Board newBoard = board.clone();
            newBoard.move(coordinate, player);
            scores.put(coordinate, score(newBoard, player));
        }
        return scores.keySet().stream().max(Comparator.comparingDouble(scores::get)).orElseThrow();
    }

    private double score(Board board, Player player) {
        int sideLength = board.getSideLength();
        double total = 0.0;
        for(int i = 0; i < board.getSize(); i++) {
            Coordinate coordinate = Coordinate.getCoordinateFromIndex(i, sideLength);
            if (board.getTokenAtCoordinate(coordinate).equals(player.getToken())) {
                if (isCorner(board, i)) {
                    total += CORNER_POINTS;
                }
                else if (isEdge(board, i)) {
                    total += EDGE_POINTS;
                }
                else {
                    total += NORMAL_POINTS;
                }
            }
        }
        return total;
    }

    private boolean isCorner(Board board, int index) {
        Coordinate coordinate = Coordinate.getCoordinateFromIndex(index, board.getSideLength());
        return (isOnEnd(board, coordinate.getRow()) && isOnEnd(board, coordinate.getColumn()));
    }

    private boolean isEdge(Board board, int index) {
        Coordinate coordinate = Coordinate.getCoordinateFromIndex(index, board.getSideLength());
        boolean topOrBottom = (isOnEnd(board, coordinate.getRow()) && !isOnEnd(board, coordinate.getColumn()));
        boolean leftOrRight = (!isOnEnd(board, coordinate.getRow()) && isOnEnd(board, coordinate.getColumn()));
        return (topOrBottom || leftOrRight);
    }

    private boolean isOnEnd(Board board, int location) {
        int end = board.getSideLength() - 1;
        return (location == 0 || location == end);
    }
}

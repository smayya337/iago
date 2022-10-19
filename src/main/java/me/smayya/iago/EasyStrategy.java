package me.smayya.iago;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class EasyStrategy extends Strategy {
    private static final double CORNER_POINTS = 4;
    private static final double EDGE_POINTS = 2;
    private static final double NORMAL_POINTS = 1;

    @Override
    public Coordinate getMove(Board board, Player player) {
        Map<Coordinate, Double> scores = new HashMap<>();
        for (Coordinate coordinate : board.getValidLocations(player)) {
            Board newBoard = board.clone();
            newBoard.move(coordinate, player);
            scores.put(coordinate, score(newBoard, player));
        }
        return scores.keySet().stream().max(Comparator.comparingDouble(scores::get)).orElse(null);
    }

    private double score(Board board, Player player) {
        double total = 0.0;
        for (int i = 0; i < Board.SIZE; i++) {
            Coordinate coordinate = Coordinate.getCoordinateFromIndex(i, Board.SIDE_LENGTH);
            if (board.getTokenAtCoordinate(coordinate).equals(player.getToken())) {
                if (board.isCorner(i)) {
                    total += CORNER_POINTS;
                } else if (board.isEdge(i)) {
                    total += EDGE_POINTS;
                } else {
                    total += NORMAL_POINTS;
                }
            }
        }
        return total;
    }
}

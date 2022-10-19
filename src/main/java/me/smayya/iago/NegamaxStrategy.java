package me.smayya.iago;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class NegamaxStrategy extends Strategy {
    private static final int DEFAULT_DEPTH = 3;
    @Override
    public Coordinate getMove(Board board, Player player) {
        Map<Coordinate, Double> scores = new HashMap<>();
        for (Coordinate coordinate : board.getValidLocations(player)) {
            Board newBoard = board.clone();
            newBoard.move(coordinate, player);
            scores.put(coordinate, negamax(newBoard, player, DEFAULT_DEPTH));
        }
        return scores.keySet().stream().max(Comparator.comparingDouble(scores::get)).orElseThrow();
    }

    private double negamax(Board board, Player lastMoved, int depth) {
        if (depth == 0) {
            return score(board, lastMoved);
        }
        Player nextMove = Player.getOpponent(lastMoved);
        HashSet<Double> scores = new HashSet<>();
        for (Coordinate coordinate : board.getValidLocations(nextMove)) {
            Board newBoard = board.clone();
            newBoard.move(coordinate, nextMove);
            scores.add(-1 * negamax(newBoard, nextMove, depth - 1));
        }
        return scores.stream().max(Double::compare).orElseThrow();
    }

    private double score(Board board, Player player) {
        final double CORNER_POINTS = 4;
        final double EDGE_POINTS = 2;
        final double NORMAL_POINTS = 1;
        final double SPOT_MULTIPLIER = 8;
        final double MOBILITY_MULTIPLIER = 16;
        double total = 0.0;
        Player opponent = Player.getOpponent(player);
        for (int i = 0; i < Board.SIZE; i++) {
            Coordinate coordinate = Coordinate.getCoordinateFromIndex(i, Board.SIDE_LENGTH);
            String token = board.getTokenAtCoordinate(coordinate);
            if (token.equals(player.getToken())) {
                if (board.isCorner(i)) {
                    total += CORNER_POINTS;
                } else if (board.isEdge(i)) {
                    total += EDGE_POINTS;
                } else {
                    total += NORMAL_POINTS;
                }
            }
            else if (token.equals(opponent.getToken())) {
                if (board.isCorner(i)) {
                    total -= CORNER_POINTS;
                } else if (board.isEdge(i)) {
                    total -= EDGE_POINTS;
                } else {
                    total -= NORMAL_POINTS;
                }
            }
        }
        total *= SPOT_MULTIPLIER;
        total += MOBILITY_MULTIPLIER * (board.getValidLocations(player).size() - board.getValidLocations(opponent).size());
        return total;
    }
}

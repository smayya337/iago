package me.smayya.iago;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class NegamaxStrategy extends Strategy {
    private static final int DEFAULT_DEPTH = 5;
    private static final double DEFAULT_ALPHA = Double.NEGATIVE_INFINITY;
    private static final double DEFAULT_BETA = Double.POSITIVE_INFINITY;

    @Override
    public Coordinate getMove(Board board, Player player) {
        Map<Coordinate, Double> scores = new HashMap<>();
        for (Coordinate coordinate : board.getValidLocations(player)) {
            Board newBoard = board.clone();
            newBoard.move(coordinate, player);
            scores.put(coordinate, negamax(newBoard, player, DEFAULT_DEPTH, DEFAULT_ALPHA, DEFAULT_BETA));
        }
        return scores.keySet().stream().max(Comparator.comparingDouble(scores::get)).orElse(null);
    }

    private double negamax(Board board, Player lastMoved, int depth, double alpha, double beta) {
        final double WIN_SCORE = Double.POSITIVE_INFINITY;
        boolean gameOver = true;
        for (Player player : Player.values()) {
            if (board.getCount(player) > 0) {
                gameOver = false;
                break;
            }
        }
        Player nextMove = Player.getOpponent(lastMoved);
        if (depth == 0) {
            return score(board, lastMoved);
        }
        else if (gameOver) {
            int lastMovedCount = board.getCount(lastMoved);
            int nextMoveCount = board.getCount(nextMove);
            if (lastMovedCount > nextMoveCount) {
                return WIN_SCORE;
            }
            else if (nextMoveCount > lastMovedCount) {
                return -1 * WIN_SCORE;
            }
            else {
                return score(board, lastMoved);
            }
        }
        double value = DEFAULT_ALPHA;
        HashSet<Double> scores = new HashSet<>();
        for (Coordinate coordinate : board.getValidLocations(nextMove)) {
            Board newBoard = board.clone();
            newBoard.move(coordinate, nextMove);
            double outcome = -1 * negamax(newBoard, nextMove, depth - 1, -1 * beta, -1 * alpha);
            value = Math.max(value, outcome);
            if (value >= beta) {
                break;
            }
            alpha = Math.max(alpha, value);
            scores.add(outcome);
        }
        return scores.stream().max(Double::compare).orElse(Double.NEGATIVE_INFINITY);
    }

    private double score(Board board, Player player) {
        final double SPOT_MULTIPLIER = 8;
        final double MOBILITY_MULTIPLIER = 16;
        double total = 0.0;
        Player opponent = Player.getOpponent(player);
        for (int i = 0; i < Board.SIZE; i++) {
            Coordinate coordinate = Coordinate.getCoordinateFromIndex(i, Board.SIDE_LENGTH);
            String token = board.getTokenAtCoordinate(coordinate);
            if (token.equals(player.getToken())) {
                total += tokenScore(board, i);
            } else if (token.equals(opponent.getToken())) {
                total -= tokenScore(board, i);
            }
        }
        total *= SPOT_MULTIPLIER;
        total += MOBILITY_MULTIPLIER * mobilityScore(board, player, opponent);
        return total;
    }

    private double tokenScore(Board board, int index) {
        final double CORNER_POINTS = 4;
        final double EDGE_POINTS = 2;
        final double NORMAL_POINTS = 1;
        if (board.isCorner(index)) {
            return CORNER_POINTS;
        } else if (board.isEdge(index)) {
            return EDGE_POINTS;
        } else {
            return NORMAL_POINTS;
        }
    }

    private double mobilityScore(Board board, Player player, Player opponent) {
        return board.getValidLocations(player).size() - board.getValidLocations(opponent).size();
    }
}

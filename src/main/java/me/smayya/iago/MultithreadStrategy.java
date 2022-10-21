package me.smayya.iago;

import java.util.*;
import java.util.concurrent.*;

public class MultithreadStrategy extends Strategy {
    private static final long TIMEOUT_MS = 1000;
    private int threads = 0;
    private ArrayList<NegamaxWorkerStrategy> results;
    private ArrayList<NegamaxWorkerStrategy> jobs;

    @Override
    public Coordinate getMove(Board board, Player player) {
        int runningThreads = 0;
        for (Thread t : Thread.getAllStackTraces().keySet()) {
            if (t.getState() == Thread.State.RUNNABLE) runningThreads++;
        }
        threads = Math.max(1, Runtime.getRuntime().availableProcessors() - runningThreads);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(threads);
        results = new ArrayList<>();
        jobs = new ArrayList<>();
        long startTime = System.currentTimeMillis();
        long endTime = startTime + TIMEOUT_MS;
        int currentDepth = 1;
        while (System.currentTimeMillis() < endTime || results.isEmpty()) {
            if (board.countAllPlayers() + currentDepth > Board.SIZE) {
                break;
            }
            if (poolIsFull()) {
                continue;
            }
            clearPool();
            long diff = endTime - System.currentTimeMillis();
            if (diff > 0) {
                NegamaxWorkerStrategy worker = new NegamaxWorkerStrategy(board, player, currentDepth);
                Future<?> future = executor.submit(worker);
                Runnable cancelTask = () -> future.cancel(true);
                executor.schedule(cancelTask, diff, TimeUnit.MILLISECONDS);
                jobs.add(worker);
                currentDepth++;
            }
        }
        clearPool();
        executor.shutdown();
        NegamaxWorkerStrategy finalOutcome = results.get(results.size() - 1);
        // System.err.println("" + finalOutcome.depth + " " + finalOutcome.getScore());
        return finalOutcome.getResult();
    }

    private boolean poolIsFull() {
        return jobs.size() == threads && !jobs.get(0).isCompleted();
    }

    private void clearPool() {
        while (jobs.size() > 0 && jobs.get(0).isCompleted()) {
            results.add(jobs.remove(0));
        }
    }

    private static class NegamaxWorkerStrategy implements Runnable {
        private static final double DEFAULT_ALPHA = Double.NEGATIVE_INFINITY;
        private static final double DEFAULT_BETA = Double.POSITIVE_INFINITY;
        private final Board board;
        private final Player player;
        private final int depth;
        private Coordinate result = null;
        private boolean completed = false;
        private Double score;

        public NegamaxWorkerStrategy(Board board, Player player, int depth) {
            this.board = board;
            this.player = player;
            this.depth = depth;
        }

        public void run() {
            Map<Coordinate, Double> scores = new HashMap<>();
            for (Coordinate coordinate : board.getValidLocations(player)) {
                Board newBoard = board.clone();
                newBoard.move(coordinate, player);
                scores.put(coordinate, negamax(newBoard, player, depth, DEFAULT_ALPHA, DEFAULT_BETA));
            }
            result = scores.keySet().stream().max(Comparator.comparingDouble(scores::get)).orElse(null);
            completed = true;
            if (result == null) {
                score = Double.NaN;
            } else {
                score = scores.get(result);
            }
        }

        private double negamax(Board board, Player lastMoved, int depth, double alpha, double beta) {
            if (depth == 0 || board.countAllPlayers() + depth >= Board.SIZE) {
                return score(board, lastMoved);
            }
            double value = DEFAULT_ALPHA;
            Player nextMove = Player.getOpponent(lastMoved);
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
            final double SPOT_MULTIPLIER = 16;
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
            final double CORNER_POINTS = 16;
            final double EDGE_POINTS = 4;
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

        public Coordinate getResult() {
            return result;
        }

        public boolean isCompleted() {
            return completed;
        }

        public Double getScore() {
            return score;
        }
    }
}

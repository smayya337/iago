package me.smayya.iago;

public abstract class Strategy {
    private static final double WIN_SCORE = Double.POSITIVE_INFINITY;
    public abstract Coordinate getMove(Board board, Player player);

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static double gameOverScore(Board board, Player lastMoved, Player nextMove) {
        int lastMovedCount = board.getCount(lastMoved);
        int nextMoveCount = board.getCount(nextMove);
        if (lastMovedCount > nextMoveCount) {
            return WIN_SCORE;
        }
        else if (nextMoveCount > lastMovedCount) {
            return -1 * WIN_SCORE;
        }
        else {
            return 0;
        }
    }
}

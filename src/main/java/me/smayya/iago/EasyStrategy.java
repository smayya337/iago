package me.smayya.iago;

public class EasyStrategy extends Strategy {
    @Override
    public Coordinate getMove(Board board, Player player) {
        return board.getValidLocations(player).stream().sorted((x1,x2) -> score(board.clone().move(x2, player)) - score(board.clone().move(x1, player))).limit(1);
    }

    private double score(Board board, Player player) {
        int sideLength = board.getSideLength();
        double total = 0.0;
        for(int i = 0; i < board.getSize(); i++) {
            Coordinate coordinate = Coordinate.getCoordinateFromIndex(i, sideLength);
            if (board.getTokenAtCoordinate(coordinate).equals(player.getToken())) {
                if (isCorner(board, i)) {
                    total += 3;
                }
                else if (isEdge(board, i)) {
                    total += 2;
                }
                else {
                    total += 1;
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

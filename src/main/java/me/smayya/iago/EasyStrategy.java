package me.smayya.iago;

public class EasyStrategy extends Strategy {
    @Override
    public Coordinate getMove(Board board, Player player) {
        return new Coordinate(0, 0);
    }
}

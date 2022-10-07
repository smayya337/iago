package me.smayya.iago;

public abstract class Strategy {
    public abstract Coordinate getMove(Board board, Player player);
}

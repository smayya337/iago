package me.smayya.iago;

public abstract class Strategy {
    public abstract Coordinate getMove(Board board, Player player);

    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

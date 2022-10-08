package me.smayya.iago;

public class Coordinate {
    private final int row;
    private final int column;

    public Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public static int getIndexFromCoordinate(Coordinate coordinate, int rows) {
        int startOfRow = coordinate.getRow() * rows;
        return startOfRow + coordinate.getColumn();
    }

    public static Coordinate getCoordinateFromIndex(int index, int rows) {
        int rowNumber = index / rows;
        int columnNumber = index % rows;
        return new Coordinate(rowNumber, columnNumber);
    }

    public boolean equals(Coordinate obj) {
        return this.row == obj.getRow() && this.column == obj.getColumn();
    }
}

package me.smayya.iago;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return row == that.row && column == that.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public boolean isUp(Coordinate coordinate) {
        return column == coordinate.getColumn() && coordinate.getRow() < row;
    }

    public boolean isDown(Coordinate coordinate) {
        return column == coordinate.getColumn() && coordinate.getRow() > row;
    }

    public boolean isLeft(Coordinate coordinate) {
        return row == coordinate.getRow() && coordinate.getColumn() < row;
    }

    public boolean isRight(Coordinate coordinate) {
        return row == coordinate.getRow() && coordinate.getColumn() > row;
    }

    public boolean isUpLeft(Coordinate coordinate) {
        return row - coordinate.getRow() == column - coordinate.getColumn() && coordinate.getRow() < row && coordinate.getColumn() < column;
    }

    public boolean isUpRight(Coordinate coordinate) {
        return row - coordinate.getRow() == column - coordinate.getColumn() && coordinate.getRow() < row && coordinate.getColumn() > column;
    }

    public boolean isDownLeft(Coordinate coordinate) {
        return row - coordinate.getRow() == column - coordinate.getColumn() && coordinate.getRow() > row && coordinate.getColumn() < column;
    }

    public boolean isDownRight(Coordinate coordinate) {
        return row - coordinate.getRow() == column - coordinate.getColumn() && coordinate.getRow() > row && coordinate.getColumn() > column;
    }
}

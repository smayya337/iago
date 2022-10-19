package me.smayya.iago;

import java.util.Scanner;

public class TerminalUserInterface extends UserInterface {

    public void display(Board board) {
        System.out.println(prettyPrintBoard(board));
    }
    public String prettyPrintBoard(Board board) {
        int sideLength = board.getSideLength();
        String outputString = topRow();
        for (int row = 0; row < sideLength; row++) {
            outputString += TerminalBorder.SIDE_CHARACTER.getCharacter();
            for (int column = 0; column < sideLength; column++) {
                Coordinate coordinate = new Coordinate(row, column);
                outputString += board.getTokenAtCoordinate(coordinate);
            }
            outputString += TerminalBorder.SIDE_CHARACTER.getCharacter();
            if (row < sideLength - 1) {
                outputString += "\n";
            }
        }
        outputString += bottomRow();
        return outputString;
    }

    private String topRow() {
        String outputString = TerminalBorder.TOP_LEFT_CORNER.getCharacter();
        for(int i = 0; i < Board.SIDE_LENGTH; i++) {
            outputString += TerminalBorder.TOP_BOTTOM_CHARACTER.getCharacter();
        }
        outputString += TerminalBorder.TOP_RIGHT_CORNER.getCharacter();
        return outputString;
    }

    private String bottomRow() {
        String outputString = TerminalBorder.BOTTOM_LEFT_CORNER.getCharacter();
        for(int i = 0; i < Board.SIDE_LENGTH; i++) {
            outputString += TerminalBorder.TOP_BOTTOM_CHARACTER.getCharacter();
        }
        outputString += TerminalBorder.BOTTOM_RIGHT_CORNER.getCharacter();
        return outputString;
    }

    public Coordinate getUserCoordinates() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Row to place tile (top row is 1): ");
        int row = sc.nextInt() - 1;
        System.out.println();
        System.out.print("Column to place tile (left-most column is 1): ");
        int column = sc.nextInt() - 1;
        System.out.println();
        return new Coordinate(row, column);
    }

    enum TerminalBorder {
        TOP_LEFT_CORNER ("┌"),
        TOP_RIGHT_CORNER ("┐"),
        BOTTOM_LEFT_CORNER ("└"),
        BOTTOM_RIGHT_CORNER ("┘"),
        TOP_BOTTOM_CHARACTER ("─"),
        SIDE_CHARACTER ("│");

        private final String character;

        TerminalBorder(String character) {
            this.character = character;
        }

        String getCharacter() {
            return character;
        }
    }
}

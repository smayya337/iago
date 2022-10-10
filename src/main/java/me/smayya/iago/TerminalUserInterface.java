package me.smayya.iago;

import java.util.Scanner;

public class TerminalUserInterface extends UserInterface {
    private static final String TOP_LEFT_CORNER = "┌";
    private static final String TOP_RIGHT_CORNER = "┐";
    private static final String BOTTOM_LEFT_CORNER = "└";
    private static final String BOTTOM_RIGHT_CORNER = "┘";
    private static final String TOP_BOTTOM_CHARACTER = "─";
    private static final String SIDE_CHARACTER = "│";

    public void display(Board board) {
        System.out.println(prettyPrintBoard(board));
    }
    public String prettyPrintBoard(Board board) {
        int sideLength = board.getSideLength();
        String outputString = topRow(sideLength);
        for (int row = 0; row < sideLength; row++) {
            outputString += SIDE_CHARACTER;
            for (int column = 0; column < sideLength; column++) {
                Coordinate coordinate = new Coordinate(row, column);
                outputString += board.getTokenAtCoordinate(coordinate);
            }
            outputString += SIDE_CHARACTER;
            if (row < sideLength - 1) {
                outputString += "\n";
            }
        }
        outputString += bottomRow(sideLength);
        return outputString;
    }

    private String topRow(int sideLength) {
        String outputString = TOP_LEFT_CORNER;
        for(int i = 0; i < sideLength; i++) {
            outputString += TOP_BOTTOM_CHARACTER;
        }
        outputString += TOP_RIGHT_CORNER;
        return outputString;
    }

    private String bottomRow(int sideLength) {
        String outputString = BOTTOM_LEFT_CORNER;
        for(int i = 0; i < sideLength; i++) {
            outputString += TOP_BOTTOM_CHARACTER;
        }
        outputString += BOTTOM_RIGHT_CORNER;
        return outputString;
    }

    public Coordinate getUserCoordinates() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Input coordinates row,column of your move (top left is 0,0): ");
        String[] coordinateStrings = sc.nextLine().split(",");
        int row = Integer.parseInt(coordinateStrings[0]);
        int column = Integer.parseInt(coordinateStrings[1]);
        return new Coordinate(row, column);
    }
}

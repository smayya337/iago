package me.smayya.iago;

import java.util.Scanner;

public class TerminalUserInterface extends UserInterface {
    private static final String PLAYER_STRATEGY_STRING = "PLAYER";

    @Override
    public void play() {
        Strategy strategy1 = getStrategyFromCommandLine(1);
        Strategy strategy2 = getStrategyFromCommandLine(2);
        Game game = new Game(strategy1, strategy2);
        while (!game.isOver()) {
            display(game.getBoard());
            Player currentPlayer = game.getCurrentPlayer();
            Strategy currentStrategy = game.getPlayerStrategy(currentPlayer);
            if (currentStrategy != null) {
                Coordinate playerMove = getUserCoordinates();
                game.move(playerMove, currentPlayer);
            }
            else {
                game.move(currentPlayer);
            }
        }
    }

    private Strategy getStrategyFromCommandLine(int playerNumber) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select a computer strategy for player " + playerNumber + "! Available strategies: ");
        for (AvailableStrategies strategy : AvailableStrategies.values()) {
            System.out.println("  * " + strategy.toString());
        }
        System.out.println("Type PLAYER to control this player yourself.");
        Strategy output = null;
        while (output == null) {
            try {
                System.out.print("Player " + playerNumber + ": ");
                String strat = sc.next();
                if (strat.equalsIgnoreCase(PLAYER_STRATEGY_STRING)) {
                    return null;
                }
                output = AvailableStrategies.getStrategyByName(strat).getStrategy();
            } catch (IllegalArgumentException e) {
                System.err.println("That's not a valid option!");
            }
        }
        sc.close();
        return output;
    }

    public void display(Board board) {
        System.out.println(prettyPrintBoard(board));
    }

    public String prettyPrintBoard(Board board) {
        StringBuilder outputString = new StringBuilder(topRow());
        for (int row = 0; row < Board.SIDE_LENGTH; row++) {
            outputString.append(TerminalBorder.SIDE_CHARACTER.getCharacter());
            for (int column = 0; column < Board.SIDE_LENGTH; column++) {
                Coordinate coordinate = new Coordinate(row, column);
                outputString.append(board.getTokenAtCoordinate(coordinate));
            }
            outputString.append(TerminalBorder.SIDE_CHARACTER.getCharacter());
            if (row < Board.SIDE_LENGTH - 1) {
                outputString.append("\n");
            }
        }
        outputString.append(bottomRow());
        return outputString.toString();
    }

    private String topRow() {
        return TerminalBorder.TOP_LEFT_CORNER.getCharacter() + String.valueOf(TerminalBorder.TOP_BOTTOM_CHARACTER.getCharacter()).repeat(Board.SIDE_LENGTH) +
                TerminalBorder.TOP_RIGHT_CORNER.getCharacter();
    }

    private String bottomRow() {
        return TerminalBorder.BOTTOM_LEFT_CORNER.getCharacter() + String.valueOf(TerminalBorder.TOP_BOTTOM_CHARACTER.getCharacter()).repeat(Board.SIDE_LENGTH) +
                TerminalBorder.BOTTOM_RIGHT_CORNER.getCharacter();
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

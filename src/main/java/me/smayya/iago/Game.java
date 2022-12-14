package me.smayya.iago;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private final Board board;
    private final Map<Player, Strategy> players;
    private Player currentPlayer;

    public Game(Board board, Strategy player1, Strategy player2) {
        this.board = board;
        this.players = initializePlayers(player1, player2);
        currentPlayer = Player.BLACK;
    }

    public Game(Strategy player1, Strategy player2) {
        this(new Board(), player1, player2);
    }

    private Map<Player, Strategy> initializePlayers(Strategy player1, Strategy player2) {
        Map<Player, Strategy> players = new HashMap<>();
        players.put(Player.BLACK, player1);
        players.put(Player.WHITE, player2);
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public Map<Player, Strategy> getPlayers() {
        return players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Strategy getPlayerStrategy(Player player) {
        return players.get(player);
    }

    public boolean isOver() {
        return board.gameOver();
    }

    public Player getWinner() {
        return board.getWinner();
    }

    public boolean isTie() {
        return isOver() && getWinner() == null;
    }

    public void move(Coordinate coordinate, Player player) {
        if (isOver()) {
            throw new RuntimeException("Game is already over!");
        }
        if (coordinate != null) {
            board.move(coordinate, player);
        }
        swapPlayers();
    }

    public void move(Player player) {
        Strategy strategy = players.get(player);
        if (strategy == null) {
            throw new RuntimeException("Player " + player.name() + " is not controlled by the AI!");
        }
        Coordinate coordinate = strategy.getMove(board, player);
        move(coordinate, player);
    }

    private void swapPlayers() {
        currentPlayer = Player.getOpponent(currentPlayer);
    }
}

package me.smayya.iago;

import java.util.Arrays;

public class Game {
    private Board board;
    public Game(Board board) {
        this.board = board;
    }

    public Game() {
        this(new Board());
    }

    public Board getBoard() {
        return board;
    }

    public boolean isOver() {
        int occupiedSpaces = 0;
        for (Player player:
             Player.values()) {
            int playerSpaces = board.getCount(player);
            if (playerSpaces == 0) {
                return true;
            }
            occupiedSpaces += playerSpaces;
        }
        return occupiedSpaces == board.getSize();
    }

    public Player getWinner() {
        if (!isOver()) return null;
        return Arrays.stream(Player.values()).max((x1, x2) -> board.getCount(x1) - board.getCount(x2)).orElse(null);
    }

    public void move(Coordinate coordinate, Player player) {
        if (isOver()) {
            throw new RuntimeException("Game is already over!");
        }
        board.move(coordinate, player);
    }
}

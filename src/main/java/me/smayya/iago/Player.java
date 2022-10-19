package me.smayya.iago;

public enum Player {
    BLACK("x"), WHITE("o");

    private final String token;

    Player(String token) {
        this.token = token;
    }

    String getToken() {
        return token;
    }

    static Player getOpponent(Player player) {
        if (player.equals(Player.WHITE)) {
            return Player.BLACK;
        } else {
            return Player.WHITE;
        }
    }
}

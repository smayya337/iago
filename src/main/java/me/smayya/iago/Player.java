package me.smayya.iago;

public enum Player {
    WHITE("x"), BLACK("o");

    private final String token;

    Player(String token) {
        this.token = token;
    }

    String getToken() {
        return token;
    }
}

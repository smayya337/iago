package me.smayya.iago;

public enum TerminalBorder {
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

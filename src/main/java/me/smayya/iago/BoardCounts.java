package me.smayya.iago;

import java.util.HashMap;

public class BoardCounts {
    private final HashMap<String, Integer> counts;
    public BoardCounts(String board) {
        counts = new HashMap<>();
        for (char c :
                board.toCharArray()) {
            String cString = String.valueOf(c);
            counts.put(cString, counts.getOrDefault(cString, 0) + 1);
        }
    }

    public void update(String token, int count) {
        counts.put(token, counts.getOrDefault(token, 0) + count);
    }

    public int get(String token) {
        return counts.getOrDefault(token, 0);
    }
}

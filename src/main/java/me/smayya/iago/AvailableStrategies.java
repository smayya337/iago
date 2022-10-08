package me.smayya.iago;

public enum AvailableStrategies {
    EASY (new EasyStrategy());

    private final Strategy strategy;
    AvailableStrategies(Strategy strategy) {
        this.strategy = strategy;
    }
    Strategy getStrategy() {
        return strategy;
    }
}

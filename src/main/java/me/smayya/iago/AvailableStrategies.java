package me.smayya.iago;

public enum AvailableStrategies {
    EASY(new EasyStrategy()),
    MEDIUM(new NegamaxStrategy()),
    HARD(new MultithreadStrategy());

    private final Strategy strategy;

    AvailableStrategies(Strategy strategy) {
        this.strategy = strategy;
    }

    Strategy getStrategy() {
        return strategy;
    }

    static AvailableStrategies getStrategyByName(String name) {
        return AvailableStrategies.valueOf(name.toUpperCase());
    }
}

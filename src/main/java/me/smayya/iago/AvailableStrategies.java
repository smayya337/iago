package me.smayya.iago;

public enum AvailableStrategies {
    BASIC(new EasyStrategy()),
    NEGAMAX(new NegamaxStrategy()),
    MULTITHREAD(new MultithreadStrategy());

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

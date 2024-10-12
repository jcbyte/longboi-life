package com.spacecomplexity.longboilife;

public class GameConfig {
    private static final GameConfig gameConfig = new GameConfig();

    public float scaleFactor = 1;

    private GameConfig() {
    }

    public static GameConfig getConfig() {
        return gameConfig;
    }
}

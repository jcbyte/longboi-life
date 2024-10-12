package com.spacecomplexity.longboilife;

public class GameConfig {
    private static final GameConfig gameConfig = new GameConfig();

    public float scaleFactor = 1;
    public float cameraSpeed = 800;
    public float cameraSmoothness = 0.2f;

    private GameConfig() {
    }

    public static GameConfig getConfig() {
        return gameConfig;
    }
}

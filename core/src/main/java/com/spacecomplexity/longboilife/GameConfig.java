package com.spacecomplexity.longboilife;

public class GameConfig {
    private static final GameConfig gameConfig = new GameConfig();

    public float scaleFactor = 1;
    public float cameraSpeed = 1400;
    public float cameraKeyZoomSpeed = 3;
    public float cameraScrollZoomSpeed = 0.1f;

    private GameConfig() {
    }

    public static GameConfig getConfig() {
        return gameConfig;
    }
}

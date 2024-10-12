package com.spacecomplexity.longboilife;

public class GameConfig {
    private static final GameConfig gameConfig = new GameConfig();

    public float scaleFactor = 1;
    public float cameraSpeed = 1400;
    public boolean smoothCamera = true;
    public float cameraSmoothness = 0.35f;
    public float cameraKeyZoomSpeed = 5;
    public float cameraScrollZoomSpeed = 0.1f;

    private GameConfig() {
    }

    public static GameConfig getConfig() {
        return gameConfig;
    }
}

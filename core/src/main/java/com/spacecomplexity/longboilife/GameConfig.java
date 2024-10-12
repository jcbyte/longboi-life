package com.spacecomplexity.longboilife;

public class GameConfig {
    private static final GameConfig gameConfig = new GameConfig();

    public float scaleFactor = 1;
    public float cameraSpeed = 800;
    public boolean smoothCamera = true;
    public float cameraSmoothness = 0.35f;
    public float cameraZoomSpeed = 5;

    private GameConfig() {
    }

    public static GameConfig getConfig() {
        return gameConfig;
    }
}

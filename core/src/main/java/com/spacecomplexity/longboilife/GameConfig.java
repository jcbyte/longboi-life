package com.spacecomplexity.longboilife;

/**
 * Singleton class to contain variables relating to state/config of the game.
 */
public class GameConfig {
    private static final GameConfig gameConfig = new GameConfig();

    /**
     * The current scale factor of game for rendering.
     */
    public float scaleFactor = 1;

    /**
     * Camera speed whilst controlling with keyboard.
     */
    public float cameraSpeed = 1400;

    /**
     * Camera zooming speed whilst controlling with keyboard.
     */
    public float cameraKeyZoomSpeed = 3;

    /**
     * Camera zooming speed whilst controlling with mouse/trackpad.
     */
    public float cameraScrollZoomSpeed = 32;

    /**
     * If the game is in fullscreen mode.
     */
    public boolean fullscreen = false;

    private GameConfig() {
    }

    /**
     * Get the singleton instance of the {@link GameConfig} class.
     *
     * @return The single {@link GameConfig} class.
     */
    public static GameConfig getConfig() {
        return gameConfig;
    }
}

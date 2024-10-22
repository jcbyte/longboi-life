package com.spacecomplexity.longboilife;

import com.spacecomplexity.longboilife.building.BuildingType;

/**
 * Singleton class to contain variables relating to state/config of the game.
 */
public class GameState {
    private static final GameState gameState = new GameState();

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

    /**
     * The amount of money the player currently has.
     */
    public float money = 800000;

    /**
     * The current satisfaction score.
     */
    public float satisfactionScore = 0.5f;

    /**
     * The currently selected and about to be placed building.
     * <p>
     * If {@code null} then nothing is selected.
     */
    public BuildingType selectedBuilding = null;

    private GameState() {
    }

    /**
     * Get the singleton instance of the {@link GameState} class.
     *
     * @return The single {@link GameState} class.
     */
    public static GameState getState() {
        return gameState;
    }
}

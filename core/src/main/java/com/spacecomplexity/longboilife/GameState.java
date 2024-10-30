package com.spacecomplexity.longboilife;

import com.spacecomplexity.longboilife.building.Building;
import com.spacecomplexity.longboilife.building.BuildingType;

import java.util.HashMap;

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
     * The current scale factor of the UI.
     */
    public float uiScaleFactor = 1;

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
     * The building selected to be placed.
     * <p>
     * If {@code null} then nothing is selected.
     */
    public BuildingType placingBuilding = null;

    /**
     * The currently selected building on the map.
     * <p>
     * If {@code null} then nothing is selected.
     */
    public Building selectedBuilding = null;

    /**
     * The currently selected building to be moved.
     * <p>
     * If {@code null} then nothing is selected.
     */
    public Building movingBuilding = null;

    /**
     * If the game is currently paused.
     */
    public boolean paused = false;

    /**
     * The current count of buildings.
     * <p>
     * This should be modified by {@link GameState#getBuildingCount(BuildingType)} and {@link GameState#changeBuildingCount(BuildingType, int)} not directly.
     * <p>
     * This is initialised in the constructor.
     */
    public HashMap<BuildingType, Integer> buildingsCount = new HashMap<>();

    /**
     * Helper function to get the number of a specified building.
     *
     * @param buildingType the building type to get.
     * @return the building count for this specific building.
     */
    public Integer getBuildingCount(BuildingType buildingType) {
        Integer count = buildingsCount.get(buildingType);
        // If this has not yet been set return 0
        if (count == null) {
            return 0;
        }

        return count;
    }

    /**
     * Helper function to change the number of a specified building.
     *
     * @param buildingType the building type to change.
     * @param change       the amount to change it by.
     */
    public void changeBuildingCount(BuildingType buildingType, int change) {
        int count = getBuildingCount(buildingType);
        buildingsCount.put(buildingType, count + change);
    }

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

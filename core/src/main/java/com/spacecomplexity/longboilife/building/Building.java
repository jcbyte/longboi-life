package com.spacecomplexity.longboilife.building;

import com.spacecomplexity.longboilife.utils.Vector2Int;

/**
 * Represents a building in the game.
 */
public class Building {
    private final BuildingType type;
    private Vector2Int position;

    /**
     * Constructs a building instance given the specific type.
     *
     * @param type     the type of building to create.
     * @param position the position of this building in the world.
     */
    public Building(BuildingType type, Vector2Int position) {
        this.type = type;
        this.position = position;
    }

    /**
     * Get the building type.
     *
     * @return the building type.
     */
    public BuildingType getType() {
        return type;
    }

    /**
     * Get the buildings' location.
     *
     * @return the buildings' location.
     */
    public Vector2Int getPosition() {
        return position;
    }

    /**
     * Set the buildings' location.
     *
     * @param position the new buildings' location.
     */
    public void setPosition(Vector2Int position) {
        this.position = position;
    }
}

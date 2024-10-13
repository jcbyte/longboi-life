package com.spacecomplexity.longboilife.building;

import com.spacecomplexity.longboilife.utils.Vector2Int;

/**
 * Class representing a building placed in the world.
 */
public class PlacedBuilding {
    private Building building;
    private Vector2Int location;

    /**
     * Create a placed building instance with specified attributes.
     *
     * @param building the instance of the placed building.
     * @param location the location of the placed building.
     */
    public PlacedBuilding(Building building, Vector2Int location) {
        this.building = building;
        this.location = location;
    }
}

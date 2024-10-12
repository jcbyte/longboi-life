package com.spacecomplexity.longboilife.building;

import com.spacecomplexity.longboilife.utils.Vector2Int;

public class PlacedBuilding {
    private Building building;
    private Vector2Int location;

    public PlacedBuilding(Building building, Vector2Int location) {
        this.building = building;
        this.location = location;
    }
}

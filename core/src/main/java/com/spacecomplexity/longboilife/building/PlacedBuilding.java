package com.spacecomplexity.longboilife.building;

import com.spacecomplexity.longboilife.utils.Vector2Int;

public class PlacedBuilding {
    private BuildingType buildingType;
    private Vector2Int location;

    public PlacedBuilding(BuildingType buildingType, Vector2Int location) {
        this.buildingType = buildingType;
        this.location = location;
    }
}

package com.spacecomplexity.longboilife;

public class PlacedBuilding {
    BuildingType buildingType;
    Vector2Int location;

    PlacedBuilding(BuildingType buildingType, Vector2Int location) {
        this.buildingType = buildingType;
        this.location = location;
    }
}

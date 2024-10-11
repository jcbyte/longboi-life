package com.spacecomplexity.longboilife.building;

import com.spacecomplexity.longboilife.utils.Vector2Int;

public class PlacedBuilding {
    BuildingType buildingType;
    Vector2Int location;

    PlacedBuilding(BuildingType buildingType, Vector2Int location) {
        this.buildingType = buildingType;
        this.location = location;
    }
}

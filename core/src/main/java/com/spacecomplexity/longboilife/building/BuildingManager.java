package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

import java.util.EnumMap;

public class BuildingManager {
    private final EnumMap<Building, BuildingData> buildingDescriptions;

    public BuildingManager() {
        buildingDescriptions = new EnumMap<>(Building.class);

        buildingDescriptions.put(Building.Greggs, new BuildingData(
            BuildingType.Food,
            new Texture("tiles/grass.png"),
            new Vector2Int(2, 2),
            2000
        ));
        // todo this with all buildings
    }

    public BuildingData getBuildingData(Building building) throws ClassNotFoundException {
        BuildingData data = buildingDescriptions.get(building);
        if (data == null) {
            throw new ClassNotFoundException();
        }

        return data;
    }
}

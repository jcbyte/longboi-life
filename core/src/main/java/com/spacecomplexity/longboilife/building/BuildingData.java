package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

public class BuildingData {
    BuildingType type;
    Texture tex;
    Vector2Int size;
    float cost;

    BuildingData(BuildingType type, Texture tex, Vector2Int size, float cost) {
        this.type = type;
        this.tex = tex;
        this.size = size;
        this.cost = cost;
    }
}

package com.spacecomplexity.longboilife;

import com.badlogic.gdx.graphics.Texture;

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

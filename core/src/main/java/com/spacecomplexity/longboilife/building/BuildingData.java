package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

public class BuildingData {
    public BuildingType type;
    public Texture tex;
    public Vector2Int size;
    public float cost;
    // todo these should be constant

    public BuildingData(BuildingType type, Texture tex, Vector2Int size, float cost) {
        this.type = type;
        this.tex = tex;
        this.size = size;
        this.cost = cost;
    }
}

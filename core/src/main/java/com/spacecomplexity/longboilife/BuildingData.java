package com.spacecomplexity.longboilife;

import com.badlogic.gdx.graphics.Texture;

public class BuildingData {
    BuildingType type;
    Texture tex;

    BuildingData(BuildingType type, Texture tex) {
        this.type = type;
        this.tex = tex;
    }
}

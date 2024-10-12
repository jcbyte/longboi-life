package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

public class GreggsBuilding extends Building {
    private static final GreggsBuilding instance = new GreggsBuilding();

    GreggsBuilding() {
        super(new Texture("greggs.png"), new Vector2Int(2, 2), 200);
    }

    public static GreggsBuilding getInstance() {
        return instance;
    }
}

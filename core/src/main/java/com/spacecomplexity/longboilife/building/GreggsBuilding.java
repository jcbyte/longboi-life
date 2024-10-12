package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

public class GreggsBuilding extends Building {
    private static GreggsBuilding instance;

    GreggsBuilding() {
        super(new Texture("greggs.png"), new Vector2Int(2, 2), 200);
    }

    public static GreggsBuilding getInstance() {
        if (instance == null) {
            instance = new GreggsBuilding();
        }
        return instance;
    }
}

package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

public class WaterTile extends Tile {
    private static WaterTile instance;

    private WaterTile() {
        super(new Texture("water.png"), false);
    }

    public static WaterTile getInstance() {
        if (instance == null) {
            instance = new WaterTile();
        }
        return instance;
    }
}

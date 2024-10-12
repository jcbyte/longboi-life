package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

public class WaterTile extends Tile {
    private static final WaterTile instance = new WaterTile();

    private WaterTile() {
        super(new Texture("water.png"), false);
    }

    public static WaterTile getInstance() {
        return instance;
    }
}

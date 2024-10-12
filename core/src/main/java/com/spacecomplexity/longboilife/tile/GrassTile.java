package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

public class GrassTile extends Tile {
    private static final GrassTile instance = new GrassTile();

    private GrassTile() {
        super(new Texture("grass.png"), true);
    }

    public static GrassTile getInstance() {
        return instance;
    }
}

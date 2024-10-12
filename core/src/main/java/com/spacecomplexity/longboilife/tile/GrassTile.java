package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

public class GrassTile extends Tile {
    private static GrassTile instance;

    private GrassTile() {
        super(new Texture("grass.png"), true);
    }

    public static GrassTile getInstance() {
        if (instance == null) {
            instance = new GrassTile();
        }
        return instance;
    }
}

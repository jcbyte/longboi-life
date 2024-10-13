package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

/**
 * A singleton class representing a water tile.
 */
public class WaterTile extends Tile {
    private static WaterTile instance;

    /**
     * Create the instance of the tile with specified properties.
     */
    private WaterTile() {
        super(new Texture("water.png"), false);
    }

    /**
     * Get the singleton instance of the water tile.
     *
     * @return the single water tile instance.
     */
    public static WaterTile getInstance() {
        // Use lazy loading to create the tile only when it is required
        if (instance == null) {
            instance = new WaterTile();
        }
        return instance;
    }
}

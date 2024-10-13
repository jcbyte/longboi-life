package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

/**
 * A singleton class representing a grass tile.
 */
public class GrassTile extends Tile {
    private static GrassTile instance;

    /**
     * Create the instance of the tile with specified properties.
     */
    private GrassTile() {
        super(new Texture("grass.png"), true);
    }

    /**
     * Get the singleton instance of the grass tile.
     *
     * @return the single grass tile instance.
     */
    public static GrassTile getInstance() {
        // Use lazy loading to create the tile only when it is required
        if (instance == null) {
            instance = new GrassTile();
        }
        return instance;
    }
}

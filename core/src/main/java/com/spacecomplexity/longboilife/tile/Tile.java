package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

/**
 * Represents a generic tile in the game.
 * <p>
 * All subclasses should be implemented as singleton classes.
 */
public abstract class Tile {
    private final Texture texture;
    private final boolean isBuildable;

    /**
     * Constructs a tile instance with specified attributes.
     *
     * @param texture    the texture associated with this tile.
     * @param canBuildOn whether the tile can be built on.
     */
    protected Tile(Texture texture, boolean canBuildOn) {
        this.texture = texture;
        this.isBuildable = canBuildOn;
    }

    /**
     * Returns the singleton instance of the implemented class.
     *
     * @return the single instance of the implemented class.
     */
    public static Tile getInstance() {
        throw new UnsupportedOperationException("Method should be implemented in subclasses as all subclasses should be singletons");
    }

    /**
     * Dispose of resources allocated with this tile.
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Get the texture associated with the tile.
     *
     * @return the associated texture.
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Get whether the tile is buildable.
     *
     * @return whether the tile is buildable.
     */
    public boolean isBuildable() {
        return isBuildable;
    }
}

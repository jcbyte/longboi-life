package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

/**
 * Represents a generic building in the game.
 * <p>
 * All subclasses should be implemented as singleton classes.
 */
public abstract class Building {
    private final Texture texture;
    private final Vector2Int size;
    private final float cost;

    /**
     * Constructs a building instance with the specified attributes.
     *
     * @param texture the texture associated with this building.
     * @param size    the size of this building, in tiles.
     * @param cost    the cost to place this building.
     */
    protected Building(Texture texture, Vector2Int size, float cost) {
        this.texture = texture;
        this.size = size;
        this.cost = cost;
    }

    /**
     * Returns the singleton instance of the implemented class.
     *
     * @return the single instance of the implemented class.
     */
    public static Building getInstance() {
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
     * Get the size of the building.
     *
     * @return the size of the building.
     */
    public Vector2Int getSize() {
        return size;
    }

    /**
     * Get the cost of the building.
     *
     * @return the cost of the building.
     */
    public float getCost() {
        return cost;
    }
}

package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

/**
 * Contains a list of all buildings, including there default data.
 */
public enum BuildingType {
    GREGGS(new Texture("greggs.png"), new Vector2Int(2, 2), 200);

    private final Texture texture;
    private final Vector2Int size;
    private final float cost;

    /**
     * Create a {@link BuildingType} with specified attributes.
     *
     * @param texture the texture representing the building.
     * @param size    the size of the building (in tiles).
     * @param cost    the cost to place the building.
     */
    BuildingType(Texture texture, Vector2Int size, float cost) {
        this.texture = texture;
        this.size = size;
        this.cost = cost;
    }

    /**
     * Get the texture associated with the building.
     *
     * @return the texture associated with the building.
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

    /**
     * Will dispose of the all loaded assets (like textures).
     * <p>
     * <strong>Warning:</strong> Once disposed of no attributes will be able to be reloaded, which could lead to undefined behaviour.
     */
    public void dispose() {
        texture.dispose();
    }
}

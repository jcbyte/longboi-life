package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

import java.util.stream.Stream;

/**
 * Contains a list of all buildings, including there default data.
 */
public enum BuildingType {
    GREGGS(new Texture("buildings/greggs.png"), new Vector2Int(2, 2), BuildingCategory.FOOD, 200),
    LIBRARY(new Texture("buildings/library.png"), new Vector2Int(4, 4), BuildingCategory.EDUCATIONAL, 200),
    GYM(new Texture("buildings/gym.png"), new Vector2Int(3, 3), BuildingCategory.RECREATIONAL, 200),
    ;

    private final Texture texture;
    private final Vector2Int size;
    private final BuildingCategory category;
    private final float cost;

    /**
     * Create a {@link BuildingType} with specified attributes.
     *
     * @param texture the texture representing the building.
     * @param size    the size of the building (in tiles).
     * @param cost    the cost to place the building.
     */
    BuildingType(Texture texture, Vector2Int size, BuildingCategory category, float cost) {
        this.texture = texture;
        this.size = size;
        this.category = category;
        this.cost = cost;
    }


    public Texture getTexture() {
        return texture;
    }


    public Vector2Int getSize() {
        return size;
    }

    public BuildingCategory getCategory() {
        return category;
    }

    public float getCost() {
        return cost;
    }

    public static BuildingType[] getBuildingsOfType(BuildingCategory category) {
        return Stream.of(BuildingType.values())
            .filter(buildingType -> buildingType.getCategory().equals(category))
            .toArray(BuildingType[]::new);

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

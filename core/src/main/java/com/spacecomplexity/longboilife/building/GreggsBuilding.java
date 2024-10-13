package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

/**
 * A singleton class representing a Greggs building.
 */
public class GreggsBuilding extends Building {
    private static GreggsBuilding instance;

    /**
     * Create the instance of the building with specified properties.
     */
    GreggsBuilding() {
        super(new Texture("greggs.png"), new Vector2Int(2, 2), 200);
    }

    /**
     * Get the singleton instance of the greggs building.
     *
     * @return the single greggs building instance.
     */
    public static GreggsBuilding getInstance() {
        if (instance == null) {
            instance = new GreggsBuilding();
        }
        return instance;
    }
}

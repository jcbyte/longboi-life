package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

public abstract class Building {
    private final Texture texture;
    private final Vector2Int size;
    private final float cost;

    protected Building(Texture texture, Vector2Int size, float cost) {
        this.texture = texture;
        this.size = size;
        this.cost = cost;
    }

    public static Building getInstance() {
        throw new UnsupportedOperationException("Method should be implemented in subclasses as all subclasses should be singletons");
    }

    public Texture getTexture() {
        return texture;
    }

    public Vector2Int getSize() {
        return size;
    }

    public float getCost() {
        return cost;
    }
}

package com.spacecomplexity.longboilife.building;

import com.badlogic.gdx.graphics.Texture;
import com.spacecomplexity.longboilife.utils.Vector2Int;

public abstract class Building {
    private final Texture tex;
    private final Vector2Int size;
    private final float cost;

    public Building(Texture tex, Vector2Int size, float cost) {
        this.tex = tex;
        this.size = size;
        this.cost = cost;
    }

    public Texture getTex() {
        return tex;
    }

    public Vector2Int getSize() {
        return size;
    }

    public float getCost() {
        return cost;
    }
}

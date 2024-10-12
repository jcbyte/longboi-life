package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

public abstract class Tile {
    private final Texture texture;
    private final boolean isBuildable;

    protected Tile(Texture texture, boolean canBuildOn) {
        this.texture = texture;
        this.isBuildable = canBuildOn;
    }

    public Texture getTexture() {
        return texture;
    }

    public boolean isBuildable() {
        return isBuildable;
    }

    public static Tile getInstance() {
        throw new UnsupportedOperationException("Method should be implemented in subclasses as all subclasses should be singletons");
    }

}

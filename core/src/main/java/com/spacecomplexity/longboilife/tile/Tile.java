package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

public abstract class Tile {
    private final Texture tex;
    private final boolean isBuildable;

    public Tile(Texture tex, boolean canBuildOn) {
        this.tex = tex;
        this.isBuildable = canBuildOn;
    }

    public Texture getTex() {
        return tex;
    }

    public boolean isBuildable() {
        return isBuildable;
    }
}

package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

public class TileData {
    public Texture tex;
    public boolean canBuildOn;
    // todo these should be constant

    public TileData(Texture tex, boolean canBuildOn) {
        this.tex = tex;
        this.canBuildOn = canBuildOn;
    }
}

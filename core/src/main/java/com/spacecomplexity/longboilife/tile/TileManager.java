package com.spacecomplexity.longboilife.tile;

import com.badlogic.gdx.graphics.Texture;

import java.util.EnumMap;

public class TileManager {
    private final EnumMap<TileType, TileData> tileDescriptions;

    public TileManager() {
        tileDescriptions = new EnumMap<>(TileType.class);

        tileDescriptions.put(TileType.GRASS, new TileData(
            new Texture("grass.png")
        ));
        tileDescriptions.put(TileType.WATER, new TileData(
            new Texture("water.png")
        ));
    }

    public TileData getTileData(TileType tileType) throws ClassNotFoundException {
        TileData data = tileDescriptions.get(tileType);
        if (data == null) {
            throw new ClassNotFoundException();
        }

        return data;
    }
}

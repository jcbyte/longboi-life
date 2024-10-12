package com.spacecomplexity.longboilife.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacecomplexity.longboilife.World;

public class RenderUtils {
    // todo should TILE_SIZE be here?
    public static final int TILE_SIZE = 20; // in pixels

    public static void drawWorld(SpriteBatch batch, World world) {
        for (int x = 0; x < world.size.x; x++) {
            for (int y = 0; y < world.size.y; y++) {
                batch.draw(world.world[x][y].getTexture(), x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
    }
}

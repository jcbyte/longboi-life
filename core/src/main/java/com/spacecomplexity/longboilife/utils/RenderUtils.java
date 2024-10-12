package com.spacecomplexity.longboilife.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacecomplexity.longboilife.Constants;
import com.spacecomplexity.longboilife.world.World;

public class RenderUtils {

    public static void drawWorld(SpriteBatch batch, World world) {
        for (int x = 0; x < world.size.x; x++) {
            for (int y = 0; y < world.size.y; y++) {
                batch.draw(world.world[x][y].getTexture(), x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE);
            }
        }
    }
}

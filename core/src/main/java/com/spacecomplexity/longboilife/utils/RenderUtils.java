package com.spacecomplexity.longboilife.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacecomplexity.longboilife.Constants;
import com.spacecomplexity.longboilife.GameConfig;
import com.spacecomplexity.longboilife.world.World;

/**
 * A class used for rendering utilities.
 */
public class RenderUtils {
    /**
     * Draw the {@link World} onto the {@link SpriteBatch} given.
     *
     * @param batch the {@link SpriteBatch} to draw onto.
     * @param world the {@link World} containing map and buildings to draw.
     */
    public static void drawWorld(SpriteBatch batch, World world) {
        GameConfig gameConfig = GameConfig.getConfig();

        // For every tile
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                // y will  be flipped as libGDX coordinates start in the bottom right instead of left
                // Draw the tile texture with size specified by TILE_SIZE and the current scaling factor
                batch.draw(
                    world.getTile(x, world.getHeight() - 1 - y).getTexture(),
                    x * Constants.TILE_SIZE * gameConfig.scaleFactor,
                    y * Constants.TILE_SIZE * gameConfig.scaleFactor,
                    Constants.TILE_SIZE * gameConfig.scaleFactor,
                    Constants.TILE_SIZE * gameConfig.scaleFactor);
            }
        }
    }
}

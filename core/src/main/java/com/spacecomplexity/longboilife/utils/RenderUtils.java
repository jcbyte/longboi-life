package com.spacecomplexity.longboilife.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.spacecomplexity.longboilife.Constants;
import com.spacecomplexity.longboilife.GameConfig;
import com.spacecomplexity.longboilife.world.World;

public class RenderUtils {

    public static void drawWorld(SpriteBatch batch, World world) {
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                batch.draw(
                    world.getTile(x, world.getHeight() - 1 - y).getTexture(),
                    x * Constants.TILE_SIZE * GameConfig.getConfig().scaleFactor,
                    y * Constants.TILE_SIZE * GameConfig.getConfig().scaleFactor,
                    Constants.TILE_SIZE * GameConfig.getConfig().scaleFactor,
                    Constants.TILE_SIZE * GameConfig.getConfig().scaleFactor);
            }
        }
    }
}

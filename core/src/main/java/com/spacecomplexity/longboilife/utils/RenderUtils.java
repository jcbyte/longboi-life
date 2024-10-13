package com.spacecomplexity.longboilife.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
     * @param batch            the {@link SpriteBatch} to draw sprites to.
     * @param shapeRenderer    the {@link ShapeRenderer} to draw shapes to.
     * @param world            the {@link World} containing map and buildings to draw.
     * @param displayGridlines whether gridlines/tile borders should be drawn.
     */
    public static void drawWorld(SpriteBatch batch, ShapeRenderer shapeRenderer, World world, boolean displayGridlines) {
        GameConfig gameConfig = GameConfig.getConfig();

        batch.begin();
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
                    Constants.TILE_SIZE * gameConfig.scaleFactor
                );
            }
        }
        batch.end();

        // If we are to display gird lines do it now
        if (displayGridlines) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);

            // Draw all vertical lines
            float worldTop = world.getHeight() * Constants.TILE_SIZE * gameConfig.scaleFactor;
            for (int x = 0; x < world.getWidth(); x++) {
                float xEdge = x * Constants.TILE_SIZE * gameConfig.scaleFactor;
                shapeRenderer.line(xEdge, 0, xEdge, worldTop);
            }

            // Draw all horizontal lines
            float worldRight = world.getWidth() * Constants.TILE_SIZE * gameConfig.scaleFactor;
            for (int y = 0; y < world.getHeight(); y++) {
                float yEdge = y * Constants.TILE_SIZE * gameConfig.scaleFactor;
                shapeRenderer.line(0, yEdge, worldRight, yEdge);
            }

            shapeRenderer.end();
        }
    }
}

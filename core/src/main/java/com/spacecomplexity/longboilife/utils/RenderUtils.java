package com.spacecomplexity.longboilife.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.spacecomplexity.longboilife.Constants;
import com.spacecomplexity.longboilife.GameState;
import com.spacecomplexity.longboilife.building.Building;
import com.spacecomplexity.longboilife.building.BuildingType;
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
    public static void drawWorld(SpriteBatch batch, ShapeRenderer shapeRenderer, World world, BuildingType ghostBuilding, boolean darkened, boolean displayGridlines) {
        GameState gameState = GameState.getState();

        float cellSize = Constants.TILE_SIZE * gameState.scaleFactor;

        batch.begin();

        // If the map should be darkened then add a tint here
        if (darkened)
            batch.setColor(Color.GRAY);

        // For every tile
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                // Draw the tile texture with size specified by TILE_SIZE and the current scaling factor
                batch.draw(
                    world.getTile(x, y).getType().getTexture(),
                    x * cellSize,
                    y * cellSize,
                    cellSize,
                    cellSize
                );
            }
        }

        // For every building
        for (Building building : world.buildings) {
            Vector2Int buildingPosition = building.getPosition();
            Vector2Int buildingSize = building.getType().getSize();

            // Draw the building
            batch.draw(
                building.getType().getTexture(),
                buildingPosition.x * cellSize,
                buildingPosition.y * cellSize,
                buildingSize.x * cellSize,
                buildingSize.y * cellSize
            );
        }

        // Remove the darkened tint if there was one applied
        batch.setColor(Color.WHITE);

        // If there is a ghost building to draw, draw in on top of the tiles
        if (ghostBuilding != null) {
            Vector2Int mouse = GameUtils.getMouseOnGrid(world);

            // Check if this would be a valid position to build
            boolean invalidPlacement = !world.canBuild(ghostBuilding, mouse.x, mouse.y);

            // If an invalid position then give the building a red hue
            if (invalidPlacement) {
                batch.setColor(Color.RED);
            }

            // Draw the ghost building
            batch.draw(
                ghostBuilding.getTexture(),
                mouse.x * cellSize,
                mouse.y * cellSize,
                ghostBuilding.getSize().x * cellSize,
                ghostBuilding.getSize().y * cellSize
            );

            // If we previously set a red hue, return it to white
            if (invalidPlacement) {
                batch.setColor(Color.WHITE);
            }
        }

        batch.end();

        // If we are to display gird lines do it now
        if (displayGridlines) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.BLACK);

            // Draw all vertical lines
            float worldTop = world.getHeight() * Constants.TILE_SIZE * gameState.scaleFactor;
            for (int x = 0; x < world.getWidth(); x++) {
                float xEdge = x * Constants.TILE_SIZE * gameState.scaleFactor;
                shapeRenderer.line(xEdge, 0, xEdge, worldTop);
            }

            // Draw all horizontal lines
            float worldRight = world.getWidth() * Constants.TILE_SIZE * gameState.scaleFactor;
            for (int y = 0; y < world.getHeight(); y++) {
                float yEdge = y * Constants.TILE_SIZE * gameState.scaleFactor;
                shapeRenderer.line(0, yEdge, worldRight, yEdge);
            }

            shapeRenderer.end();
        }
    }
}

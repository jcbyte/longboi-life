package com.spacecomplexity.longboilife.world;

import com.spacecomplexity.longboilife.tile.InvalidSaveMapException;
import com.spacecomplexity.longboilife.tile.Tile;
import com.spacecomplexity.longboilife.tile.TileType;

/**
 * Class representing the saved map in a JSON format.
 * <p>
 * JSON format:
 * <pre>
 * {
 *  "map": [
 *      ["GRASS", "GRASS", "GRASS"],
 *      ["WATER", "WATER", "GRASS"]
 *  ]
 * }
 * </pre>
 * Where each element can be any valid enum name as specified in {@link TileType}.
 */
public class SaveMap {
    private String[][] map;

    public SaveMap() {
    }

    /**
     * Transforms the JSON string grid into a tile grid which {@link World} requires.
     *
     * @return the 2D {@link Tile} grid representing the world.
     * @throws InvalidSaveMapException if a tile given is not valid name assigned.
     */
    public Tile[][] getWorld() throws InvalidSaveMapException {
        // Grid generated will be transformed so it can be accessed as map[x][y]

        // Get height and width of map throwing an error if either is 0
        int height = map.length;
        if (height == 0) {
            throw new InvalidSaveMapException("World has no height");
        }
        int width = map[0].length;
        if (width == 0) {
            throw new InvalidSaveMapException("World has no width");
        }

        // Initialise the array with given width and height
        Tile[][] world = new Tile[width][height];

        // Go though each element in the object in the specified order
        for (int y = 0; y < height; y++) {
            // throw an error if any line mismatches the size of the width
            if (map[y].length != width) {
                throw new InvalidSaveMapException("World width mismatched at y: " + y);
            }
            for (int x = 0; x < width; x++) {
                // Retrieve the tile name
                // Flipping the y-axis as LibGdx draws from the bottom right, instead of top left how our json is structured
                String tileName = map[height - y - 1][x];
                try {
                    // Determine the type of tile based on value in the JSON map
                    TileType tileType = TileType.valueOf(tileName);
                    world[x][y] = new Tile(tileType);
                } catch (IllegalArgumentException e) {
                    // If the tile is invalid throw an error
                    throw new InvalidSaveMapException("Invalid tile type \"" + tileName + "\" at (" + x + ", " + y + ")");
                }
            }
        }

        return world;
    }
}

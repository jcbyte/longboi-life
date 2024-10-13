package com.spacecomplexity.longboilife.world;

import com.spacecomplexity.longboilife.tile.GrassTile;
import com.spacecomplexity.longboilife.tile.InvalidTileException;
import com.spacecomplexity.longboilife.tile.Tile;
import com.spacecomplexity.longboilife.tile.WaterTile;

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
 * Where each element can be any valid tile name.
 */
public class SaveMap {
    private String[][] map;

    public SaveMap() {
    }

    /**
     * Transforms the JSON string grid into a tile grid which {@link World} requires.
     *
     * @return the 2D {@link Tile} grid representing the world.
     * @throws InvalidTileException if a tile given is not valid name assigned.
     */
    public Tile[][] getWorld() throws InvalidTileException {
        // Grid generated will be transformed so it can be accessed as map[x][y]
        // y will also be flipped as libGDX coordinates start in the bottom right instead of left

        int height = map.length;
        int width = map[0].length;

        // Initialise the array with given width and height
        Tile[][] world = new Tile[width][height];

        // Go though each element in the object in the specified order
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                // Determine the type of tile based on value in the JSON map
                Tile tile = switch (map[y][x]) {
                    case "GRASS" -> GrassTile.getInstance();
                    case "WATER" -> WaterTile.getInstance();
                    default -> null;
                };

                // If the tile is invalid throw an error
                if (tile == null) {
                    throw new InvalidTileException("Invalid tile: " + map[x][y]);
                }

                world[x][y] = tile;
            }
        }

        return world;
    }
}

package com.spacecomplexity.longboilife.world;

import com.spacecomplexity.longboilife.tile.GrassTile;
import com.spacecomplexity.longboilife.tile.InvalidTileException;
import com.spacecomplexity.longboilife.tile.Tile;
import com.spacecomplexity.longboilife.tile.WaterTile;

public class SaveMap {
    private String[][] map;

    public SaveMap() {
    }

    public Tile[][] getWorld() throws InvalidTileException {

        int height = map.length;
        int width = map[0].length;

        Tile[][] world = new Tile[width][height];

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                Tile tile = null;

                switch (map[y][x]) {
                    case "GRASS":
                        tile = GrassTile.getInstance();
                        break;
                    case "WATER":
                        tile = WaterTile.getInstance();
                        break;
                }

                if (tile == null) {
                    throw new InvalidTileException("Invalid tile: " + map[x][y]);
                }

                world[x][y] = tile;
            }
        }

        return world;
    }
}

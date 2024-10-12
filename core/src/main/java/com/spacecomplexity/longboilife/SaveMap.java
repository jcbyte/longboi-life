package com.spacecomplexity.longboilife;

import com.spacecomplexity.longboilife.tile.GrassTile;
import com.spacecomplexity.longboilife.tile.Tile;
import com.spacecomplexity.longboilife.tile.WaterTile;

public class SaveMap {
    private String[][] map;

    public SaveMap() {
    }

    public Tile[][] getWorld() throws InvalidTileException {

        Tile[][] world = new Tile[map.length][map[0].length];
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                Tile tile = null;

                switch (map[x][y]) {
                    case "GRASS":
                        tile = new GrassTile();
                        break;
                    case "WATER":
                        tile = new WaterTile();
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

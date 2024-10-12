package com.spacecomplexity.longboilife;

import com.spacecomplexity.longboilife.tile.GrassTile;
import com.spacecomplexity.longboilife.tile.Tile;
import com.spacecomplexity.longboilife.tile.UnknownTile;
import com.spacecomplexity.longboilife.tile.WaterTile;

public class SaveMap {
    private String[][] map;

    public SaveMap() {
    }

    public Tile[][] getWorld() {

        Tile[][] world = new Tile[map.length][map[0].length];
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                Tile tile;

                switch (map[x][y]) {
                    case "GRASS":
                        tile = new GrassTile();
                        break;
                    case "WATER":
                        tile = new WaterTile();
                        break;
                    default:
                        tile = new UnknownTile();
                        break;
                }

                world[x][y] = tile;
            }
        }

        return world;
    }
}

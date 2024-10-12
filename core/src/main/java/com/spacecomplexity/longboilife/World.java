package com.spacecomplexity.longboilife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.spacecomplexity.longboilife.building.PlacedBuilding;
import com.spacecomplexity.longboilife.tile.Tile;
import com.spacecomplexity.longboilife.tile.TileManager;
import com.spacecomplexity.longboilife.tile.TileType;
import com.spacecomplexity.longboilife.utils.Vector2Int;

import java.io.FileNotFoundException;
import java.util.Vector;

public class World {
    private Vector2Int size;
    private Tile[][] world;
    private Vector<PlacedBuilding> buildings;

    public World(String filename) throws FileNotFoundException, ClassNotFoundException {
        loadMap(filename);
        buildings = new Vector<>();
    }

    private void loadMap(String filename) throws FileNotFoundException, ClassNotFoundException {
        Json json = new Json();
        FileHandle file = Gdx.files.local("map.json");

        if (!file.exists()) {
            throw new FileNotFoundException();
        }

        TileManager tileManager = new TileManager();

        SaveMap map = json.fromJson(SaveMap.class, file.readString());

        size = new Vector2Int(map.map.length, map.map[0].length);

        world = new Tile[size.x][size.y];
        for (int x = 0; x < size.x; x++) {
            for (int y = 0; y < size.y; y++) {
                TileType tileType = map.map[x][y];
                world[x][y] = new Tile(tileType, !tileManager.getTileData(tileType).canBuildOn);
            }
        }
    }
}

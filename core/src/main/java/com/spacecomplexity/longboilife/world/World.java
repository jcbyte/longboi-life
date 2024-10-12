package com.spacecomplexity.longboilife.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.spacecomplexity.longboilife.building.PlacedBuilding;
import com.spacecomplexity.longboilife.tile.InvalidTileException;
import com.spacecomplexity.longboilife.tile.Tile;
import com.spacecomplexity.longboilife.utils.Vector2Int;

import java.io.FileNotFoundException;
import java.util.Vector;

public class World {
    public Vector2Int size;
    public Tile[][] world;
    public Vector<PlacedBuilding> buildings;

    public World(String filename) throws FileNotFoundException, InvalidTileException {
        loadMap(filename);
        buildings = new Vector<>();
    }

    private void loadMap(String filename) throws FileNotFoundException, InvalidTileException {
        Json json = new Json();
        FileHandle file = Gdx.files.local(filename);

        if (!file.exists()) {
            throw new FileNotFoundException("File does not exist: " + filename);
        }

        SaveMap saveMap = json.fromJson(SaveMap.class, file.readString());
        world = saveMap.getWorld();
        size = new Vector2Int(world.length, world[0].length);
    }
}

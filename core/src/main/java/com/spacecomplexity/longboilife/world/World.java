package com.spacecomplexity.longboilife.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.spacecomplexity.longboilife.building.Building;
import com.spacecomplexity.longboilife.tile.InvalidSaveMapException;
import com.spacecomplexity.longboilife.tile.Tile;

import java.io.FileNotFoundException;
import java.util.Vector;

/**
 * Represents the game world containing the base tiles and buildings.
 */
public class World {
    private Tile[][] world;
    public Vector<Building> buildings;

    /**
     * Creates a new world loaded from a map JSON file.
     *
     * @param filename the name of the JSON file.
     * @throws FileNotFoundException   if the specified file does not exist.
     * @throws InvalidSaveMapException if the map contains invalid tile names.
     */
    public World(String filename) throws FileNotFoundException, InvalidSaveMapException {
        world = getMap(filename);
        buildings = new Vector<>();
    }

    /**
     * Loads the map from JSON file and returns the tile grid.
     *
     * @param filename the name of the JSON file.
     * @return the {@link Tile} grid representing the world extracted from the JSON file.
     * @throws FileNotFoundException   if the specified file does not exist.
     * @throws InvalidSaveMapException if the map contains invalid tile names.
     */
    private Tile[][] getMap(String filename) throws FileNotFoundException, InvalidSaveMapException {
        Json json = new Json();
        FileHandle file = Gdx.files.local(filename);

        // If the file does not exist throw an exception
        if (!file.exists()) {
            throw new FileNotFoundException("File does not exist: \"" + filename + "\"");
        }

        try {
            // Deserialize the JSON data into the SaveMap object
            SaveMap saveMap = json.fromJson(SaveMap.class, file.readString());
            // Return the Tile[][] from this object.
            return saveMap.getWorld();
        } catch (SerializationException e) {
            // If there is an issue in deserialising throw an exception
            throw new InvalidSaveMapException("Issue deserialising map save file \"" + filename + "\": " + e.getMessage());
        }
    }

    /**
     * Retrieves the tile at the specified coordinates.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return the {@link Tile} at the specified coordinates.
     */
    public Tile getTile(int x, int y) {
        return world[x][y];
    }

    /**
     * Get the height of the world.
     *
     * @return the height of the world.
     */
    public int getHeight() {
        return world[0].length;
    }

    /**
     * Get the width of the world.
     *
     * @return the width of the world.
     */
    public int getWidth() {
        return world.length;
    }
}

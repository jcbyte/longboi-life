package com.spacecomplexity.longboilife.world;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.spacecomplexity.longboilife.building.Building;
import com.spacecomplexity.longboilife.building.BuildingType;
import com.spacecomplexity.longboilife.tile.InvalidSaveMapException;
import com.spacecomplexity.longboilife.tile.Tile;
import com.spacecomplexity.longboilife.utils.Vector2Int;

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
     * @param mapFile the handle to the JSON file containing map.
     * @throws FileNotFoundException   if the specified file does not exist.
     * @throws InvalidSaveMapException if the map contains invalid tile names.
     */
    public World(FileHandle mapFile) throws FileNotFoundException, InvalidSaveMapException {
        world = getMap(mapFile);
        buildings = new Vector<>();
    }

    /**
     * Loads the map from JSON file and returns the tile grid.
     *
     * @param mapFile the handle to the JSON file containing map.
     * @return the {@link Tile} grid representing the world extracted from the JSON file.
     * @throws FileNotFoundException   if the specified file does not exist.
     * @throws InvalidSaveMapException if the map contains invalid tile names.
     */
    private Tile[][] getMap(FileHandle mapFile) throws FileNotFoundException, InvalidSaveMapException {
        Json json = new Json();

        // If the file does not exist throw an exception
        if (!mapFile.exists()) {
            throw new FileNotFoundException("File does not exist: \"" + mapFile.name() + "\"");
        }

        try {
            // Deserialize the JSON data into the SaveMap object
            SaveMap saveMap = json.fromJson(SaveMap.class, mapFile.readString());
            // Return the Tile[][] from this object.
            return saveMap.getWorld();
        } catch (SerializationException e) {
            // If there is an issue in deserialising throw an exception
            throw new InvalidSaveMapException("Issue deserialising map save file \"" + mapFile.name() + "\": " + e.getMessage());
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

    public int getHeight() {
        return world[0].length;
    }

    public int getWidth() {
        return world.length;
    }

    /**
     * Check if a building can be placed at a specific location in the world.
     *
     * @param building the building we wish to place.
     * @param x        the x coordinate of the building.
     * @param y        the y coordinate of the building.
     * @return whether this is a valid position to build the building.
     */
    public boolean canBuild(BuildingType building, int x, int y) {
        Vector2Int buildingSize = building.getSize();

        // If the building goes off the edge of the map it is not valid
        if (x + buildingSize.x > getWidth() || y + buildingSize.y > getHeight()) {
            return false;
        }

        // Check if every tile underneath this building is buildable
        for (int xi = x; xi < x + buildingSize.x; xi++) {
            for (int yi = y; yi < y + buildingSize.y; yi++) {
                if (!getTile(xi, yi).isBuildable()) {
                    // If a single tile is not then the building placement is invalid
                    return false;
                }
            }
        }

        // If all tiles are then the building placement is valid
        return true;
    }

    /**
     * Build a building at a specific location in the world.
     *
     * @param buildingType the building we wish to place.
     * @param x            the x coordinate of the building.
     * @param y            the y coordinate of the building.
     */
    public void build(BuildingType buildingType, int x, int y) {
        // If we cannot build then throw an exception
        if (!canBuild(buildingType, x, y)) {
            throw new IllegalStateException("Building \"" + buildingType.name() + "\" cannot build at (" + x + ", " + y + ")");
        }

        // Create the building instance
        Building building = new Building(buildingType, new Vector2Int(x, y));


        // Set every tile underneath this building to un-buildable and assign the reference
        Vector2Int buildingSize = buildingType.getSize();
        for (int xi = x; xi < x + buildingSize.x; xi++) {
            for (int yi = y; yi < y + buildingSize.y; yi++) {
                Tile tile = getTile(xi, yi);
                tile.setBuildingRef(building);
                tile.setBuildable(false);

            }
        }

        buildings.add(building);
    }

    /**
     * Remove a building from the world.
     *
     * @param building the building to remove.
     */
    public void demolish(Building building) {
        // Set every tile underneath this building to its original buildable state and remove the building reference
        Vector2Int buildingSize = building.getType().getSize();
        Vector2Int buildingPosition = building.getPosition();
        for (int xi = buildingPosition.x; xi < buildingPosition.x + buildingSize.x; xi++) {
            for (int yi = buildingPosition.y; yi < buildingPosition.y + buildingSize.y; yi++) {
                Tile tile = getTile(xi, yi);
                tile.setBuildingRef(null);
                tile.setBuildable(tile.getType().isNaturallyBuildable());
            }
        }

        buildings.remove(building);
    }
}

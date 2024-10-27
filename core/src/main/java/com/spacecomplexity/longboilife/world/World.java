package com.spacecomplexity.longboilife.world;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.SerializationException;
import com.spacecomplexity.longboilife.building.Building;
import com.spacecomplexity.longboilife.building.BuildingCategory;
import com.spacecomplexity.longboilife.building.BuildingType;
import com.spacecomplexity.longboilife.pathways.PathwayPositions;
import com.spacecomplexity.longboilife.tile.InvalidSaveMapException;
import com.spacecomplexity.longboilife.tile.Tile;
import com.spacecomplexity.longboilife.utils.Vector2Int;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Vector;

/**
 * Represents the game world containing the base tiles and buildings.
 */
public class World {
    private Tile[][] world;
    public Vector<Building> buildings;
    private PathwayPositions[][] pathways;

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
        pathways = new PathwayPositions[getWidth()][getHeight()];
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

    /**
     * Retrieves the pathway position at the specified coordinates.
     *
     * @param x the x coordinate.
     * @param y the y coordinate.
     * @return the pathway position the specified coordinates.
     */
    public PathwayPositions getPathwayPosition(int x, int y) {
        return pathways[x][y];
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
        // Create the building instance
        Building building = new Building(buildingType, new Vector2Int(x, y));

        // Try to build this
        build(building);
    }

    /**
     * Build a building at a specific location in the world.
     *
     * @param building the building to build.
     * @param x        the x coordinate of the building.
     * @param y        the y coordinate of the building.
     */
    public void build(Building building, int x, int y) {
        // Update the building instance
        building.setPosition(new Vector2Int(x, y));

        // Try to build this
        build(building);
    }

    /**
     * Build a building in the world.
     *
     * @param building the building to build
     */
    public void build(Building building) {
        // Get the coordinates of where to build the building
        int x = building.getPosition().x;
        int y = building.getPosition().y;

        // If we cannot build then throw an exception
        if (!canBuild(building.getType(), x, y)) {
            throw new IllegalStateException("Building \"" + building.getType().name() + "\" cannot build at (" + x + ", " + y + ")");
        }

        // Set every tile underneath this building to un-buildable and assign the reference
        Vector2Int buildingSize = building.getType().getSize();
        for (int xi = x; xi < x + buildingSize.x; xi++) {
            for (int yi = y; yi < y + buildingSize.y; yi++) {
                Tile tile = getTile(xi, yi);
                tile.setBuildingRef(building);
                tile.setBuildable(false);

            }
        }

        buildings.add(building);

        // If building is a pathway then calculate and add the type to the pathways grid
        if (building.getType().getCategory() == BuildingCategory.PATHWAY) {
            updatePathwayPosition(x, y);
        }
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

        if (building.getType().getCategory() == BuildingCategory.PATHWAY) {
            pathways[buildingPosition.x][buildingPosition.y] = null;
        }
    }


    /**
     * Map of encoded neighbouring paths and the respective layout of this path.
     */
    private static final HashMap<Integer, PathwayPositions> pathwayPositionsMap = new HashMap<>() {
        {
            put(0b1010, PathwayPositions.TOP_BOTTOM);
            put(0b0101, PathwayPositions.LEFT_RIGHT);
            put(0b1001, PathwayPositions.LEFT_TOP);
            put(0b1100, PathwayPositions.TOP_RIGHT);
            put(0b0110, PathwayPositions.RIGHT_BOTTOM);
            put(0b0011, PathwayPositions.BOTTOM_LEFT);
            put(0b1101, PathwayPositions.LEFT_TOP_RIGHT);
            put(0b1110, PathwayPositions.TOP_RIGHT_BOTTOM);
            put(0b0111, PathwayPositions.RIGHT_BOTTOM_LEFT);
            put(0b1011, PathwayPositions.BOTTOM_LEFT_TOP);
            put(0b1111, PathwayPositions.TOP_LEFT_BOTTOM_RIGHT);
        }
    };

    /**
     * Update the pathways on the board recursively starting at the coordinates given.
     *
     * @param x the x coordinate of the pathway.
     * @param y the x coordinate of the pathway.
     */
    private void updatePathwayPosition(int x, int y) {
        // If there is no building on this tile then do nothing.
        Building thisBuilding = getTile(x, y).getBuildingRef();
        if (thisBuilding == null) {
            return;
        }
        // If the building is not a pathway then ignore
        BuildingType thisBuildingType = thisBuilding.getType();
        if (thisBuildingType.getCategory() != BuildingCategory.PATHWAY) {
            return;
        }

        // Call recursive function with an empty vector as no pathways have been set
        updatePathwayPosition(x, y, new Vector<>());
    }

    /**
     * Update the pathways on the board recursively starting at the coordinates given.
     * <p>
     * This should only be called recursive from this function and the starter function.
     *
     * @param x               the x coordinate of the pathway.
     * @param y               the x coordinate of the pathway.
     * @param updatedPathways a list of all pathways which have already been updated.
     */
    private void updatePathwayPosition(int x, int y, Vector<Vector2Int> updatedPathways) {
        // If this pathway has already been done then ignore
        if (updatedPathways.contains(new Vector2Int(x, y))) {
            return;
        }

        BuildingType thisBuildingType = getTile(x, y).getBuildingRef().getType();

        // Get neighbouring pathways
        boolean top = isOurPathway(x, y + 1, thisBuildingType);
        boolean right = isOurPathway(x + 1, y, thisBuildingType);
        boolean bottom = isOurPathway(x, y - 1, thisBuildingType);
        boolean left = isOurPathway(x - 1, y, thisBuildingType);

        // Encode our neighboring paths to query the map for the layout
        int code = (top ? 1 : 0) << 3 | (right ? 1 : 0) << 2 | (bottom ? 1 : 0) << 1 | (left ? 1 : 0);
        PathwayPositions position = pathwayPositionsMap.get(code);
        // If this is not specified default to straight
        if (position == null) {
            position = PathwayPositions.TOP_BOTTOM;
        }

        pathways[x][y] = position;

        // Recursively set nearby pathways positions
        // The initial checks have already been done as `isOurPathway` will only return true if these passed
        updatedPathways.add(new Vector2Int(x, y));
        if (top) {
            updatePathwayPosition(x, y + 1, updatedPathways);
        }
        if (right) {
            updatePathwayPosition(x + 1, y, updatedPathways);
        }
        if (bottom) {
            updatePathwayPosition(x, y - 1, updatedPathways);
        }
        if (left) {
            updatePathwayPosition(x - 1, y, updatedPathways);
        }
    }

    /**
     * Check if a pathway is the same as a specified one.
     *
     * @param x           the x coordinate of the pathway
     * @param y           the y coordinate of the pathway.
     * @param thisPathway the pathway to check/match too.
     * @return if the pathway at (x, y) is the same as the pathway specified.
     */
    private boolean isOurPathway(int x, int y, BuildingType thisPathway) {
        // Check this is in the worlds bounds
        if (x < 0 || x >= getWidth() || y < 0 || y >= getHeight()) {
            return false;
        }

        // Get the building on this tile
        Building ref = getTile(x, y).getBuildingRef();
        // If there is no building return false
        // If there is a building only return true if it is the same pathway as this
        return ref != null && ref.getType() == thisPathway;
    }
}

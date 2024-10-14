package com.spacecomplexity.longboilife.tile;

/**
 * Represents a tile in the game.
 */
public class Tile {
    private final TileType type;
    private boolean isBuildable;

    /**
     * Constructs a tile instance given the specific tile.
     *
     * @param type whether the tile can be built on.
     */
    public Tile(TileType type) {
        this.type = type;
        isBuildable = type.isNaturallyBuildable();
    }

    /**
     * Get the tile type.
     *
     * @return the tile type.
     */
    public TileType getType() {
        return type;
    }

    /**
     * Get whether the tile is currently able to be built on.
     *
     * @return whether the tile is buildable.
     */
    public boolean isBuildable() {
        return isBuildable;
    }

    /**
     * Set the buildable status of this tile.
     *
     * @param isBuildable the new buildable status.
     */
    public void setBuildable(boolean isBuildable) {
        this.isBuildable = isBuildable;
    }
}

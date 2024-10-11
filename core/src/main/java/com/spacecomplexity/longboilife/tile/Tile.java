package com.spacecomplexity.longboilife.tile;

public class Tile {
    private TileType type;
    private boolean occupied;

    public Tile(TileType type, boolean isOccupied) {
        this.type = type;
        this.occupied = isOccupied;
    }
}

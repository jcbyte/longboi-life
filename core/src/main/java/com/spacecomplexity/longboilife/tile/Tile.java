package com.spacecomplexity.longboilife.tile;

public class Tile {
    public TileType type;
    public boolean occupied;

    public Tile(TileType type, boolean isOccupied) {
        this.type = type;
        this.occupied = isOccupied;
    }
}

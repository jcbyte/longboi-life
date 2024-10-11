package com.spacecomplexity.longboilife;

public class Tile {
    private TileType type;
    private boolean occupied;

    Tile(TileType type, boolean isOccupied) {
        this.type = type;
        this.occupied = isOccupied;
    }
}

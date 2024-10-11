package com.spacecomplexity.longboilife;

import com.spacecomplexity.longboilife.building.PlacedBuilding;
import com.spacecomplexity.longboilife.tile.Tile;
import com.spacecomplexity.longboilife.utils.Vector2Int;

import java.util.Vector;

public class World {
    public Vector2Int size;
    public Tile[][] world;
    public Vector<PlacedBuilding> buildings;

    World(Vector2Int size) {
        this.size = size;
        world = new Tile[size.x][size.y];
        // todo initialise world
        buildings = new Vector<>();
    }
}

package com.spacecomplexity.longboilife.utils;

/**
 * Class representing a 2d Vector where each element is represented by an integer.
 */
public class Vector2Int {
    public int x;
    public int y;

    /**
     * Initialise a blank Vector, (0, 0).
     */
    public Vector2Int() {
        x = 0;
        y = 0;
    }

    /**
     * Initialise the vector with values.
     *
     * @param x the initial x value.
     * @param y the intel y value.
     */
    public Vector2Int(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

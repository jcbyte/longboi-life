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

    @Override
    public boolean equals(Object obj) {
        // Check for the same reference
        if (this == obj) return true;
        // Check class type
        if (obj == null || getClass() != obj.getClass()) return false;

        // Check if fields are equal
        Vector2Int that = (Vector2Int) obj;
        return x == that.x && y == that.y;
    }
    
    @Override
    public int hashCode() {
        int result = Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(y);
        return result;
    }
}

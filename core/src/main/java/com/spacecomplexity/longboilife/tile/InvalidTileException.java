package com.spacecomplexity.longboilife.tile;

/**
 * Exception to indicate an invalid tile was found.
 */
public class InvalidTileException extends RuntimeException {
    public InvalidTileException(String message) {
        super(message);
    }
}

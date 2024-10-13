package com.spacecomplexity.longboilife;

import com.badlogic.gdx.Input;

public enum Keybindings {
    CAMERA_UP(Input.Keys.W),
    CAMERA_LEFT(Input.Keys.A),
    CAMERA_DOWN(Input.Keys.S),
    CAMERA_RIGHT(Input.Keys.D),
    CAMERA_ZOOM_IN(Input.Keys.Q),
    CAMERA_ZOOM_OUT(Input.Keys.E);

    private final int key;

    Keybindings(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}

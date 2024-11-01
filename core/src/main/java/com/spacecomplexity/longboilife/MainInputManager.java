package com.spacecomplexity.longboilife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.spacecomplexity.longboilife.game.EventHandler;
import com.spacecomplexity.longboilife.game.GameState;
import com.spacecomplexity.longboilife.game.Keybindings;

/**
 * Input manager to handle inputs which should always be handled.
 */
public class MainInputManager extends InputAdapter {
    GameState gameState = GameState.getState();
    EventHandler eventHandler = EventHandler.getEventHandler();

    private int prevAppWidth, prevAppHeight;

    /**
     * Handles onKeyPress events.
     *
     * @param keycode one of the constants in {@link com.badlogic.gdx.Input.Keys}
     * @return true if the event was handled.
     */
    @Override
    public boolean keyDown(int keycode) {
        // If the fullscreen key is pressed then toggle fullscreen mode
        if (keycode == Keybindings.FULLSCREEN.getKey()) {
            gameState.fullscreen = !gameState.fullscreen;

            if (gameState.fullscreen) {
                // If going to fullscreen then record the current width and height to return to
                prevAppWidth = Gdx.graphics.getWidth();
                prevAppHeight = Gdx.graphics.getHeight();

                // Change to fullscreen
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            } else {
                // Change back to windowed mode, restoring the previous app width and height
                Gdx.graphics.setWindowedMode(prevAppWidth, prevAppHeight);
            }

            return true;
        }

        return false;
    }
}

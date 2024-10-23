package com.spacecomplexity.longboilife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;

public class InputManager {
    private final GameState gameState = GameState.getState();

    /**
     * Create an input manager by initialising the input processors and set attributes.
     *
     * @param inputMultiplexer to add the input processor events to the input processing.
     */
    public InputManager(InputMultiplexer inputMultiplexer) {
        inputMultiplexer.addProcessor(new InputProcessor());
    }

    /**
     * Handles continuous input which won't be processed in {@link InputManager.InputProcessor} (as this is event driven).
     * Should be called every frame (before rendering).
     */
    public void handleContinuousInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Calculate camera speed and move camera around given camera direction keys pressed
        float cameraSpeed = gameState.cameraSpeed * deltaTime * MainCamera.camera().zoom * gameState.scaleFactor;
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_UP.getKey())) {
            MainCamera.camera().position.y += cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_DOWN.getKey())) {
            MainCamera.camera().position.y -= cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_LEFT.getKey())) {
            MainCamera.camera().position.x -= cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_RIGHT.getKey())) {
            MainCamera.camera().position.x += cameraSpeed;
        }

        // Calculate camera zoom speed and zoom camera around given camera zoom keys pressed
        float cameraZoomSpeed = gameState.cameraKeyZoomSpeed * deltaTime * MainCamera.camera().zoom;
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_ZOOM_IN.getKey())) {
            MainCamera.camera().zoom += cameraZoomSpeed;
        }
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_ZOOM_OUT.getKey())) {
            MainCamera.camera().zoom -= cameraZoomSpeed;
        }
    }

    /**
     * Handles input (event driven).
     * Will be called every frame (before rendering, hence before {@link InputManager#handleContinuousInput()}).
     */
    private class InputProcessor extends InputAdapter {
        /**
         * Zoom the camera at the mouse position when the scroll wheel/trackpad is used.
         *
         * @param amountX the horizontal scroll amount, negative or positive depending on the direction the wheel was scrolled.
         * @param amountY the vertical scroll amount, negative or positive depending on the direction the wheel was scrolled.
         * @return true to show the event was handled.
         */
        @Override
        public boolean scrolled(float amountX, float amountY) {
            float deltaTime = Gdx.graphics.getDeltaTime();

            // Convert the current mouse position into world coordinates
            Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            MainCamera.camera().getCamera().unproject(mousePosition);

            // Zoom in/out at the mouses current position
            MainCamera.camera().zoomAt(amountY * gameState.cameraScrollZoomSpeed * deltaTime * MainCamera.camera().zoom, mousePosition);

            return true;
        }

        private int lastButton;

        // Used to store mouse position between dragging frames
        private float lastScreenX, lastScreenY;

        /**
         * Mouse down event.
         *
         * @param screenX The x coordinate, origin is in the upper left corner.
         * @param screenY The y coordinate, origin is in the upper left corner.
         * @param pointer the pointer for the event.
         * @param button  the button pressed.
         * @return true to show the event was handled.
         */
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            // Set the last button so we can check what to do in the touchDragged event
            lastButton = button;

            switch (button) {
                // If main button clicked
                case 0:
                    // If a building is selected then try to build this
                    if (GameState.getState().selectedBuilding != null) {
                        try {
                            EventHandler.getEventHandler().callEvent("build");
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    // Else try and select a building already on the map
                    else {
                        try {
                            EventHandler.getEventHandler().callEvent("select_building");
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    break;

                // If secondary or tertiary button clicked
                case 1:
                case 2:
                    // Record the initial touch position for the drag event
                    lastScreenX = screenX;
                    lastScreenY = screenY;
                    
                    break;
            }

            return true;
        }

        /**
         * Calculates the difference in mouse drag and moves the camera accordingly, hence allows the world to be dragged around.
         *
         * @param screenX The x coordinate, origin is in the upper left corner.
         * @param screenY The y coordinate, origin is in the upper left corner.
         * @param pointer the pointer for the event.
         * @return true to show the event was handled.
         */
        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            switch (lastButton) {
                // If secondary or tertiary button clicked
                case 1:
                case 2:
                    // Calculate the difference between last mouse position and now
                    float deltaX = screenX - lastScreenX;
                    float deltaY = screenY - lastScreenY;

                    // Move the camera the respective amount to simulate dragging
                    MainCamera.camera().position.x -= deltaX * MainCamera.camera().zoom;
                    MainCamera.camera().position.y += deltaY * MainCamera.camera().zoom;

                    lastScreenX = screenX;
                    lastScreenY = screenY;
                    break;
            }

            return true;
        }

        private int prevAppWidth, prevAppHeight;

        /**
         * Handles onKeyPress events.
         *
         * @param keycode one of the constants in {@link com.badlogic.gdx.Input.Keys}
         * @return true to show the event was handled.
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
            }

            // If the close key is pressed, send events to cancel actions
            else if (keycode == Keybindings.CLOSE.getKey()) {
                try {
                    EventHandler.getEventHandler().callEvent("close_build_menu");
                    gameState.selectedBuilding = null;
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

            // If the pause key is pressed, pause/resume the game
            else if (keycode == Keybindings.PAUSE.getKey()) {
                try {
                    EventHandler.getEventHandler().callEvent(GameState.getState().paused ? "resume_game" : "pause_game");
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }

            return true;
        }
    }
}

package com.spacecomplexity.longboilife;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.math.Vector3;

public class InputManager {
    private final GameConfig gameConfig = GameConfig.getConfig();
    private CameraManager camera;

    /**
     * Create an input manager by initialising the input processors and set attributes.
     *
     * @param inputMultiplexer to add the input processor events to the input processing.
     * @param camera           modified by input events.
     */
    public InputManager(InputMultiplexer inputMultiplexer, CameraManager camera) {
        inputMultiplexer.addProcessor(new InputProcessor());
        this.camera = camera;
    }

    /**
     * Handles continuous input which won't be processed in {@link InputManager.InputProcessor} (as this is event driven).
     * Should be called every frame (before rendering).
     */
    public void handleContinuousInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        // Calculate camera speed and move camera around given camera direction keys pressed
        float cameraSpeed = gameConfig.cameraSpeed * deltaTime * camera.zoom * gameConfig.scaleFactor;
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_UP.getKey())) {
            camera.position.y += cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_DOWN.getKey())) {
            camera.position.y -= cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_LEFT.getKey())) {
            camera.position.x -= cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_RIGHT.getKey())) {
            camera.position.x += cameraSpeed;
        }

        // Calculate camera zoom speed and zoom camera around given camera zoom keys pressed
        float cameraZoomSpeed = gameConfig.cameraKeyZoomSpeed * deltaTime * camera.zoom;
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_ZOOM_IN.getKey())) {
            camera.zoom += cameraZoomSpeed;
        }
        if (Gdx.input.isKeyPressed(Keybindings.CAMERA_ZOOM_OUT.getKey())) {
            camera.zoom -= cameraZoomSpeed;
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
            camera.getCamera().unproject(mousePosition);

            // Zoom in/out at the mouses current position
            camera.zoomAt(amountY * gameConfig.cameraScrollZoomSpeed * deltaTime * camera.zoom, mousePosition);

            return true;
        }

        // Used to store mouse position between dragging frames
        private float lastScreenX, lastScreenY;

        /**
         * Record the initial touch position for the drag event.
         *
         * @param screenX The x coordinate, origin is in the upper left corner.
         * @param screenY The y coordinate, origin is in the upper left corner.
         * @param pointer the pointer for the event.
         * @param button  the button pressed.
         * @return true to show the event was handled.
         */
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            lastScreenX = screenX;
            lastScreenY = screenY;

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
            // Calculate the difference between last mouse position and now
            float deltaX = screenX - lastScreenX;
            float deltaY = screenY - lastScreenY;

            // Move the camera the respective amount to simulate dragging
            camera.position.x -= deltaX * camera.zoom;
            camera.position.y += deltaY * camera.zoom;

            lastScreenX = screenX;
            lastScreenY = screenY;

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
                gameConfig.fullscreen = !gameConfig.fullscreen;

                if (gameConfig.fullscreen) {
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

            return true;
        }
    }
}

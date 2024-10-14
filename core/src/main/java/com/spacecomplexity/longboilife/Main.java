package com.spacecomplexity.longboilife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.tile.InvalidSaveMapException;
import com.spacecomplexity.longboilife.utils.RenderUtils;
import com.spacecomplexity.longboilife.world.World;

import java.io.FileNotFoundException;


/**
 * The main class (entry point) for game logic.
 * Responsible for handling rendering and input.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private World world;

    private CameraManager camera;
    private Viewport viewport;

    private final GameConfig gameConfig = GameConfig.getConfig();

    /**
     * Responsible for setting up the game initial state.
     * Called when the game is first run.
     */
    @Override
    public void create() {
        // Creates a new World object from "map.json" file
        try {
            world = new World("map.json");
        } catch (FileNotFoundException | InvalidSaveMapException e) {
            throw new RuntimeException(e);
        }

        // Initialise SpriteBatch and ShapeRender for rendering
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // Initialises camera from CameraManager and Viewport
        camera = new CameraManager(world);
        viewport = new ScreenViewport(camera.getCamera());
        // Calculates the scale factor based initial screen height
        int screenHeight = Gdx.graphics.getHeight();
        gameConfig.scaleFactor = screenHeight / (float) Constants.SCALING_1_HEIGHT;

        // Position camera in the center of the world map
        camera.position.set(new Vector3(
            world.getWidth() * Constants.TILE_SIZE * gameConfig.scaleFactor / 2,
            world.getHeight() * Constants.TILE_SIZE * gameConfig.scaleFactor / 2,
            0
        ));

        // Set up an InputProcessor to handle user inputs
        Gdx.input.setInputProcessor(new InputProcessor());
    }

    /**
     * Renders the game world, and make calls to handle continuous inputs.
     * Called every frame.
     */
    @Override
    public void render() {
        // Call to handles any constant input
        handleConstantInput();

        // Clear the screen
        ScreenUtils.clear(0, 0, 0, 1f);

        // Applies viewport transformations and updates camera ready for rendering
        viewport.apply();
        camera.update();
        // Update the SpriteBatch and ShapeRenderer to match the updates camera
        batch.setProjectionMatrix(camera.getCombinedMatrix());
        shapeRenderer.setProjectionMatrix(camera.getCombinedMatrix());

        // Draw the world on screen
        RenderUtils.drawWorld(batch, shapeRenderer, world, true);
    }

    /**
     * Handles continuous input which won't be processed in {@link InputProcessor} (as this is event driven).
     * Should be called every frame (before rendering).
     */
    private void handleConstantInput() {
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
     * Will be called every frame (before rendering, hence before {@link Main#handleConstantInput()}).
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

    /**
     * Handles resizing events, to ensure the game can be scaled.
     * Called when the game window is resized.
     *
     * @param width  the new width in pixels.
     * @param height the new height in pixels.
     */
    @Override
    public void resize(int width, int height) {
        // Updates viewport to match new window size
        viewport.update(width, height, false);

        // Recalculate scaling factor with new height
        gameConfig.scaleFactor = height / (float) Constants.SCALING_1_HEIGHT;
    }

    /**
     * Release all resources held by the game.
     * Called when the game is being closed.
     */
    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }
}

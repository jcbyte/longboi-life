package com.spacecomplexity.longboilife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.tile.InvalidSaveMapException;
import com.spacecomplexity.longboilife.ui.UIManager;
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
    private UIManager ui;
    private InputManager inputManager;

    private CameraManager camera;
    private Viewport viewport;

    private World world;

    private final GameConfig gameConfig = GameConfig.getConfig();

    /**
     * Responsible for setting up the game initial state.
     * Called when the game is first run.
     */
    @Override
    public void create() {
        // Creates a new World object from "map.json" file
        try {
            world = new World(Gdx.files.internal("map.json"));
        } catch (FileNotFoundException | InvalidSaveMapException e) {
            throw new RuntimeException(e);
        }

        // Create a new timer for 5 minutes
        TimerManager.getTimerManager().getTimer().setTimer(5 * 60 * 1000);

        // Create an input multiplexer to handle input from all sources
        InputMultiplexer inputMultiplexer = new InputMultiplexer();

        // Initialise SpriteBatch and ShapeRender for rendering
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // Initialises camera with CameraManager
        camera = new CameraManager(world);

        // Initialise viewport for rescaling
        viewport = new ScreenViewport(camera.getCamera());

        // Initialise UI elements with UIManager
        ui = new UIManager(inputMultiplexer);

        // Calculates the scale factor based initial screen height
        int screenHeight = Gdx.graphics.getHeight();
        gameConfig.scaleFactor = screenHeight / (float) Constants.SCALING_1_HEIGHT;

        // Position camera in the center of the world map
        camera.position.set(new Vector3(
            world.getWidth() * Constants.TILE_SIZE * gameConfig.scaleFactor / 2,
            world.getHeight() * Constants.TILE_SIZE * gameConfig.scaleFactor / 2,
            0
        ));

        // Set up an InputManager to handle user inputs
        inputManager = new InputManager(inputMultiplexer, camera);
        // Set the Gdx input processor to handle all our input processes
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    /**
     * Renders the game world, and make calls to handle continuous inputs.
     * Called every frame.
     */
    @Override
    public void render() {
        // Call to handles any constant input
        inputManager.handleContinuousInput();

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

        // Render the UI
        ui.render();
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

        // Rescale UI
        ui.resize(width, height);
    }

    /**
     * Release all resources held by the game.
     * Called when the game is being closed.
     */
    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        ui.dispose();
    }
}

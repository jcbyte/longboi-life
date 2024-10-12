package com.spacecomplexity.longboilife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.tile.InvalidTileException;
import com.spacecomplexity.longboilife.utils.RenderUtils;
import com.spacecomplexity.longboilife.world.World;

import java.io.FileNotFoundException;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private World world;

    private OrthographicCamera camera;
    private Viewport viewport;

    GameConfig gameConfig = GameConfig.getConfig();

    @Override
    public void create() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        int screenHeight = Gdx.graphics.getHeight();
        gameConfig.scaleFactor = screenHeight / (float) Constants.SCALING_1_HEIGHT;

        try {
            world = new World("map.json");
        } catch (FileNotFoundException | InvalidTileException e) {
            throw new RuntimeException(e);
        }

        camera.position.set(new Vector3(
            world.getWidth() * Constants.TILE_SIZE * gameConfig.scaleFactor / 2,
            world.getHeight() * Constants.TILE_SIZE * gameConfig.scaleFactor / 2,
            0
        ));

        Gdx.input.setInputProcessor(new InputProcessor());
    }

    @Override
    public void render() {
        handleConstantInput();

        ScreenUtils.clear(0, 0, 0, 1f);

        viewport.apply();
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        RenderUtils.drawWorld(batch, world);
        batch.end();
    }

    private void handleConstantInput() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        float cameraSpeed = gameConfig.cameraSpeed * deltaTime * camera.zoom * gameConfig.scaleFactor;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.position.y += cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.position.y -= cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.position.x -= cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.position.x += cameraSpeed;
        }
        
        float cameraZoomSpeed = gameConfig.cameraKeyZoomSpeed * deltaTime * camera.zoom;

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom = MathUtils.clamp(camera.zoom + cameraZoomSpeed, Constants.MIN_ZOOM, Constants.MAX_ZOOM);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom = MathUtils.clamp(camera.zoom - cameraZoomSpeed, Constants.MIN_ZOOM, Constants.MAX_ZOOM);
        }

        // todo clamp camera position/zoom (possibly abstract this oop)
//        cameraTargetPosition = new Vector2(
//            MathUtils.clamp(cameraTargetPosition.x, 0, world.getWidth() * Constants.TILE_SIZE * gameConfig.scaleFactor),
//            MathUtils.clamp(cameraTargetPosition.y, 0, world.getHeight() * Constants.TILE_SIZE * gameConfig.scaleFactor)
//        );

        // todo document
        // todo readme
    }

    private class InputProcessor extends InputAdapter {
        @Override
        public boolean scrolled(float amountX, float amountY) {
            Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(mousePosition);

            float newZoom = MathUtils.clamp(camera.zoom + amountY * gameConfig.cameraScrollZoomSpeed, Constants.MIN_ZOOM, Constants.MAX_ZOOM);

            float zoomFactor = newZoom / camera.zoom;

            camera.position.x += (mousePosition.x - camera.position.x) * (1 - zoomFactor);
            camera.position.y += (mousePosition.y - camera.position.y) * (1 - zoomFactor);

            // Set the new zoom level
            camera.zoom = newZoom;

            return false;
        }

        private float lastScreenX, lastScreenY;
        private boolean smoothCameraPreviously;

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            lastScreenX = screenX;
            lastScreenY = screenY;

            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            float deltaX = screenX - lastScreenX;
            float deltaY = screenY - lastScreenY;

            camera.position.x -= deltaX * camera.zoom;
            camera.position.y += deltaY * camera.zoom;

            lastScreenX = screenX;
            lastScreenY = screenY;

            return false;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);

        int screenHeight = Gdx.graphics.getHeight();
        gameConfig.scaleFactor = screenHeight / (float) Constants.SCALING_1_HEIGHT;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

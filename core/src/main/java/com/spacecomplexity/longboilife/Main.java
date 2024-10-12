package com.spacecomplexity.longboilife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
    private Vector2 cameraTargetPosition = new Vector2();

    @Override
    public void create() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);
        int screenHeight = Gdx.graphics.getHeight();
        GameConfig.getConfig().scaleFactor = screenHeight / (float) Constants.SCALING_1_HEIGHT;

        try {
            world = new World("map.json");
        } catch (FileNotFoundException | InvalidTileException e) {
            throw new RuntimeException(e);
        }

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

        float cameraSpeed = GameConfig.getConfig().cameraSpeed * deltaTime * camera.zoom;

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cameraTargetPosition.y += cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cameraTargetPosition.y -= cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cameraTargetPosition.x -= cameraSpeed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cameraTargetPosition.x += cameraSpeed;
        }

        Vector3 cameraTargetPositionV3 = new Vector3(cameraTargetPosition.x, cameraTargetPosition.y, camera.position.z);
        if (GameConfig.getConfig().smoothCamera) {
            camera.position.lerp(cameraTargetPositionV3, GameConfig.getConfig().cameraSmoothness);
        } else {
            camera.position.set(cameraTargetPositionV3);
        }

        float cameraZoomSpeed = GameConfig.getConfig().cameraZoomSpeed * deltaTime * camera.zoom;

        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom = MathUtils.clamp(camera.zoom + cameraZoomSpeed, Constants.MIN_ZOOM, Constants.MAX_ZOOM);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {
            camera.zoom = MathUtils.clamp(camera.zoom - cameraZoomSpeed, Constants.MIN_ZOOM, Constants.MAX_ZOOM);
        }
    }

    private class InputProcessor extends InputAdapter {
        @Override
        public boolean scrolled(float amountX, float amountY) {
            camera.zoom = MathUtils.clamp(camera.zoom + amountY * 0.1f, Constants.MIN_ZOOM, Constants.MAX_ZOOM);
            return false;
        }

        private float lastScreenX, lastScreenY;
        private boolean smoothCameraPreviously;

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            lastScreenX = screenX;
            lastScreenY = screenY;

            smoothCameraPreviously = GameConfig.getConfig().smoothCamera;
            GameConfig.getConfig().smoothCamera = false;

            return true;
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            float deltaX = screenX - lastScreenX;
            float deltaY = screenY - lastScreenY;

            cameraTargetPosition.x -= deltaX * camera.zoom;
            cameraTargetPosition.y += deltaY * camera.zoom;

            lastScreenX = screenX;
            lastScreenY = screenY;

            return false;
        }

        @Override
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
            GameConfig.getConfig().smoothCamera = smoothCameraPreviously;

            return true;
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);

        int screenHeight = Gdx.graphics.getHeight();
        GameConfig.getConfig().scaleFactor = screenHeight / (float) Constants.SCALING_1_HEIGHT;
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}

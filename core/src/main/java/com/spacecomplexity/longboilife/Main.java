package com.spacecomplexity.longboilife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
//        viewport = new FitViewport((int) (screenHeight * Constants.ASPECT_RATIO), screenHeight, camera);
        viewport = new ScreenViewport(camera);
        int screenHeight = Gdx.graphics.getHeight();
        GameConfig.getConfig().scaleFactor = screenHeight / (float) Constants.SCALING_1_HEIGHT;

        try {
            world = new World("map.json");
        } catch (FileNotFoundException | InvalidTileException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render() {
        handleInput();

        ScreenUtils.clear(0, 0, 0, 1f);

        viewport.apply();
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        RenderUtils.drawWorld(batch, world);
        batch.end();
    }

    private void handleInput() {
        float speed = GameConfig.getConfig().cameraSpeed * Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cameraTargetPosition.y += speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cameraTargetPosition.y -= speed;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cameraTargetPosition.x -= speed;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            cameraTargetPosition.x += speed;
        }

        camera.position.lerp(new Vector3(cameraTargetPosition.x, cameraTargetPosition.y, 0), GameConfig.getConfig().cameraSmoothness);
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

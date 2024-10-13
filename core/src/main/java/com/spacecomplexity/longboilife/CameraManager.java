package com.spacecomplexity.longboilife;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.spacecomplexity.longboilife.world.World;

public class CameraManager {
    private OrthographicCamera camera;
    private World world;
    private GameConfig gameConfig = GameConfig.getConfig();
    public Vector3 position;
    public float zoom;

    public CameraManager(World world) {
        camera = new OrthographicCamera();
        this.world = world;
        position = new Vector3();
        zoom = 1;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void update() {
        position.set(new Vector3(
            MathUtils.clamp(position.x, 0, world.getWidth() * Constants.TILE_SIZE * gameConfig.scaleFactor),
            MathUtils.clamp(position.y, 0, world.getHeight() * Constants.TILE_SIZE * gameConfig.scaleFactor),
            0
        ));
        camera.position.set(position);

        zoom = MathUtils.clamp(zoom, Constants.MIN_ZOOM, Constants.MAX_ZOOM);
        camera.zoom = zoom;

        camera.update();
    }

    public Matrix4 getCombinedMatrix() {
        return camera.combined;
    }

    // todo add some utility functions (zoom, scroll etc)

}

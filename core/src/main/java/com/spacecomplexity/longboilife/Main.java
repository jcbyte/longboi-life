package com.spacecomplexity.longboilife;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.spacecomplexity.longboilife.tile.TileManager;

import java.io.FileNotFoundException;


/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    final int TILE_SIZE = 20; // in pixels

    private SpriteBatch batch;
    private World world;

    @Override
    public void create() {
        batch = new SpriteBatch();

        try {
            world = new World("map.json");
        } catch (FileNotFoundException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render() {
        TileManager tileManager = new TileManager();

        ScreenUtils.clear(0, 0, 0, 1f);
        batch.begin();

        try {
            for (int x = 0; x < world.size.x; x++) {
                for (int y = 0; y < world.size.y; y++) {
                    batch.draw(tileManager.getTileData(world.world[x][y].type).tex, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
//        image.dispose(); // todo dispose of textures
    }
}

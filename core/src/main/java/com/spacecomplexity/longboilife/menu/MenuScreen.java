package com.spacecomplexity.longboilife.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.spacecomplexity.longboilife.Main;

/**
 * Main class to control the menu screen.
 */
public class MenuScreen implements Screen {
    private final Main game;

    public MenuScreen(Main game) {
        this.game = game;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        // Clear the screen
        ScreenUtils.clear(0.35f, 0, 0.4f, 1f);

        // Switch to the game screen when the screen is touched
        if (Gdx.input.isTouched()) {
            game.switchScreen(Main.ScreenType.GAME);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

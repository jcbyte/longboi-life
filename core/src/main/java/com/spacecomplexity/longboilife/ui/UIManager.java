package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.Timer;

/**
 * Class to manage the UI in the game.
 */
public class UIManager {
    private Viewport viewport;

    private Stage stage;
    private Skin skin;
    private Table table;

    private UIElement[] uiElements;

    /**
     * Initialise UI elements needed for the game.
     *
     * @param timer timer for {@link UIClockMenu} to use.
     */
    public UIManager(Timer timer) {
        // Initialise viewport for rescaling
        viewport = new ScreenViewport();

        // Initialise stage
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        // Initialise root table
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Load external UI skin
        skin = new Skin(Gdx.files.internal("shadeui/skin/uiskin.json"));

        // Create our UI elements
        uiElements = new UIElement[]{
            new UIClockMenu(viewport, table, skin, timer),
            new UISatisfactionMenu(viewport, table, skin),
            new UIMoneyMenu(viewport, table, skin),
        };
    }

    /**
     * Apply and draw UI onto the screen.
     */
    public void render() {
        // Render on each of the UI elements
        for (UIElement uiElement : uiElements) {
            uiElement.render();
        }

        // Apply and then draw
        viewport.apply();
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    /**
     * Handles resizing events, to ensure the UI is scaled correctly.
     *
     * @param width  the new width in pixels.
     * @param height the new height in pixels.
     */
    public void resize(int width, int height) {
        // Updates viewport to match new window size
        viewport.update(width, height, true);

        // Run resize functions on UI elements
        for (UIElement uiElement : uiElements) {
            uiElement.resize();
        }
    }

    /**
     * Dispose of all loaded assets.
     */
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

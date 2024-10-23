package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.GameState;

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
     * @param inputMultiplexer to add the UI events to the input processing
     */
    public UIManager(InputMultiplexer inputMultiplexer) {
        // Initialise viewport for rescaling
        viewport = new ScalingViewport(Scaling.fit, 640, 480);

        // Initialise stage
        stage = new Stage(viewport);
        inputMultiplexer.addProcessor(stage);

        // Initialise root table
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Load external UI skin
        skin = new Skin(Gdx.files.internal("shadeui/skin/uiskin.json"));

        // Create our UI elements
        uiElements = new UIElement[]{
            new UIClockMenu(viewport, table, skin),
            new UISatisfactionMenu(viewport, table, skin),
            new UIMoneyMenu(viewport, table, skin),
            new UIBottomMenu(viewport, table, skin),
            new UIBuildingSelectedMenu(viewport, table, skin),
        };

//        table.debugAll();
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

    private float customNonLinearScale(float scaleFactor) {
        return (float) Math.pow(scaleFactor, 5);
    }

    /**
     * Handles resizing events, to ensure the UI is scaled correctly.
     *
     * @param width  the new width in pixels.
     * @param height the new height in pixels.
     */
    public void resize(int width, int height) {
        // Update world size to match scaling of uiScaleFactor
        viewport.setWorldSize(
            (float) width / GameState.getState().uiScaleFactor,
            (float) height / GameState.getState().uiScaleFactor
        );

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

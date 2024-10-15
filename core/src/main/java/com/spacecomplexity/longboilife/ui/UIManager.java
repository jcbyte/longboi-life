package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.util.Random;

/**
 * Class to manage the UI in the game.
 */
public class UIManager {
    private Stage stage;
    private Skin skin;
    private Table table;

    private UIClockMenu clockMenu;

    // todo UI scaling

    /**
     * Initialise UI elements needed for the game.
     */
    public UIManager() {
        // initialise stage
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        // initialise root table
        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // load external UI skin
        skin = new Skin(Gdx.files.internal("shadeui/skin/uiskin.json"));

        // create clock menu on our root table
        clockMenu = new UIClockMenu(table, skin);
    }

    /**
     * Apply and draw UI onto the screen.
     */
    public void render() {
        // For debugging
        Random random = new Random();
        clockMenu.setLabels(Integer.toString(random.nextInt()), Float.toString(random.nextFloat()));

        // Apply and then draw
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * Dispose of all loaded assets.
     */
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

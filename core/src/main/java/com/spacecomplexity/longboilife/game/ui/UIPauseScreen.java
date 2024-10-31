package com.spacecomplexity.longboilife.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.GameState;

/**
 * Class to represent the Pause Screen UI.
 */
public class UIPauseScreen extends UIElement {
    private Texture pauseTexture;
    private Image logo;

    /**
     * Initialise pause screen elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIPauseScreen(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Initialise logo
        pauseTexture = new Texture(Gdx.files.internal("ui/buttons/pause.png"));
        logo = new Image(pauseTexture);

        // Place label onto table
        table.add(logo).align(Align.center);

        // Style and place the table
        table.setSize(150, 150);
        placeTable();
    }

    public void render() {
        if (GameState.getState().paused) {
            table.setVisible(true);
        } else {
            table.setVisible(false);
        }
    }

    @Override
    protected void placeTable() {
        table.setPosition((uiViewport.getWorldWidth() - table.getWidth()) / 2, (uiViewport.getWorldHeight() - table.getHeight()) / 2);
    }

    @Override
    public void dispose() {
        super.dispose();

        pauseTexture.dispose();
    }
}

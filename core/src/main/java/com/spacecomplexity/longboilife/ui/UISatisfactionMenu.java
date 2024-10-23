package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.GameState;

/**
 * Class to represent the Satisfaction Score UI.
 */
public class UISatisfactionMenu extends UIElement {
    private Label label;
    private ProgressBar satisfactionBar;

    /**
     * Initialise satisfaction menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UISatisfactionMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Initialise label
        label = new Label("Satisfaction Score:", skin);
        label.setFontScale(1f);
        label.setColor(Color.WHITE);

        // Initialise satisfaction bar
        satisfactionBar = new ProgressBar(0, 1, 0.01f, false, skin);

        // Place elements onto table
        table.add(label).align(Align.left);
        table.row();
        table.add(satisfactionBar);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(175, 60);
        placeTable();
    }

    public void render() {
        satisfactionBar.setValue(GameState.getState().satisfactionScore);
    }

    @Override
    protected void placeTable() {
        table.setPosition(uiViewport.getWorldWidth() - table.getWidth(), uiViewport.getWorldHeight() - table.getHeight());
    }
}

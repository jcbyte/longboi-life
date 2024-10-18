package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Class to manage the UI clock.
 */
public class UISatisfactionMenu {
    private Viewport viewport;

    private Table table;

    private Label label;
    private ProgressBar satisfactionBar;

    /**
     * Initialise satisfaction menu UI elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render the clock menus container onto.
     * @param skin        the provided skin
     */
    public UISatisfactionMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        viewport = uiViewport;

        // Initialise table container
        table = new Table(skin);

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

        // Add clock table to root table
        parentTable.addActor(table);
    }

    public void render() {
        // todo set satisfaction score
    }

    /**
     * Handles resizing events, to ensure the placement of clock menu mapped correctly.
     */
    public void resize() {
        // Updates clock menu position to match new window size
        placeTable();
    }

    private void placeTable() {
        table.setPosition(viewport.getScreenWidth() - table.getWidth(), viewport.getScreenHeight() - table.getHeight());

    }
}

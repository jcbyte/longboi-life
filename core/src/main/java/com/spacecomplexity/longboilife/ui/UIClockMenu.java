package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Class to manage the UI clock.
 */
public class UIClockMenu {
    private Viewport viewport;

    private Table table;

    private Label timeLabel;

    /**
     * Initialise clock UI elements.
     *
     * @param parentTable the table to render the clock menus container onto.
     * @param skin        the provided skin
     */
    public UIClockMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        viewport = uiViewport;

        // Initialise table container
        table = new Table(skin);

        // Initialise time text label
        timeLabel = new Label("", skin);
        timeLabel.setFontScale(1.5f);
        timeLabel.setColor(Color.WHITE);

        // Place labels onto table
        table.add(timeLabel).align(Align.center);

        // Style and place the clock table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(75, 50);
        table.setPosition(0, viewport.getScreenHeight() - table.getHeight());
        table.pad(5);

        // Add clock table to root table
        parentTable.addActor(table);
    }

    /**
     * Set the labels on the clock menu.
     *
     * @param time the main text block.
     */
    public void setLabels(String time) {
        timeLabel.setText(time);
    }

    /**
     * Handles resizing events, to ensure the placement of clock menu mapped correctly.
     */
    public void resize() {
        System.out.println(viewport.getScreenHeight());

        // Updates clock menu position to match new window size
        table.setPosition(0, viewport.getScreenHeight() - table.getHeight());
    }
}

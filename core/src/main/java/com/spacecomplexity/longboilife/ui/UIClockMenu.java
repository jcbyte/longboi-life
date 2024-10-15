package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;

/**
 * Class to manage the UI clock.
 */
public class UIClockMenu {
    private Table table;

    private Label timeLabel;
    private Label descriptionLabel;

    /**
     * Initialise clock UI elements.
     *
     * @param parentTable the table to render the clock menus container onto.
     * @param skin        the provided skin
     */
    public UIClockMenu(Table parentTable, Skin skin) {
        // Initialise table container
        table = new Table(skin);

        // Initialise time text label
        timeLabel = new Label("Time", skin);
        timeLabel.setFontScale(1.5f);
        timeLabel.setColor(Color.WHITE);

        // Initialise description text label
        descriptionLabel = new Label("Description", skin);
        descriptionLabel.setFontScale(1.0f);
        descriptionLabel.setColor(Color.LIGHT_GRAY);

        // Place labels onto table
        table.add(timeLabel).align(Align.center);
        table.row();
        table.add(descriptionLabel).align(Align.center);

        // Style and place the clock table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(150, 75);
        table.setPosition(0, Gdx.graphics.getHeight() - table.getHeight());
        table.pad(5);

        // Add clock table to root table
        parentTable.addActor(table);
    }

    /**
     * Set the labels on the clock menu.
     *
     * @param time        the main text block.
     * @param description the secondary text block.
     */
    public void setLabels(String time, String description) {
        timeLabel.setText(time);
        descriptionLabel.setText(description);
    }
}

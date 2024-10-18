package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.Timer;

/**
 * Class to represent the Clock UI.
 */
public class UIClockMenu extends UIElement {
    private Label label;

    private Timer timer;

    /**
     * Initialise clock menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     * @param timer       the timer for displaying time from.
     */
    public UIClockMenu(Viewport uiViewport, Table parentTable, Skin skin, Timer timer) {
        super(uiViewport, parentTable, skin);

        this.timer = timer;

        // Initialise time label
        label = new Label("", skin);
        label.setFontScale(1.5f);
        label.setColor(Color.WHITE);

        // Place label onto table
        table.add(label).align(Align.center);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(75, 50);
        placeTable();
    }

    public void render() {
        setTime(timer.getTimeLeft() / 1000);
    }

    /**
     * Set the labels on the clock menu to the time given.
     *
     * @param time the time in seconds
     */
    public void setTime(long time) {
        // Format this onto the time label
        label.setText(String.format("%d:%02d", time / 60, time % 60));
    }

    @Override
    protected void placeTable() {
        table.setPosition(0, uiViewport.getScreenHeight() - table.getHeight());
    }
}

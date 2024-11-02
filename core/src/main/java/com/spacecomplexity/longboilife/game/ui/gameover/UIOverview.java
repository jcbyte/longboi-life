package com.spacecomplexity.longboilife.game.ui.gameover;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.game.globals.GameState;
import com.spacecomplexity.longboilife.game.ui.UIElement;

/**
 * Class to represent the Overview UI after the game is completed.
 */
public class UIOverview extends UIElement {
    private Label label;
    private TextButton button;

    /**
     * Initialise overview elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIOverview(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        String overview = String.format("Game Over\r\nSatisfaction Score: %.2f%%", GameState.getState().satisfactionScore);

        // Initialise label
        label = new Label(overview, skin);
        label.setAlignment(Align.center);
        label.setFontScale(1.2f);
        label.setColor(Color.WHITE);

        // Initialise button
        button = new TextButton("Menu", skin);
        // todo go back to menu

        // Place label onto table
        table.add(label).align(Align.center);
        table.row();
        table.add(button).padTop(5).align(Align.center);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        table.setSize(220, 100);
        placeTable();
    }

    public void render() {
    }

    @Override
    protected void placeTable() {
        table.setPosition((uiViewport.getWorldWidth() - table.getWidth()) / 2, uiViewport.getWorldHeight() - table.getHeight());
    }
}

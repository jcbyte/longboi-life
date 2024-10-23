package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.EventHandler;

/**
 * Class to represent the pop-up menu after selecting a placed building.
 */
public class UIBuildingSelectedMenu extends UIElement {
    TextButton moveButton;
    TextButton sellButton;

    Viewport uiViewport;

    /**
     * Initialise selected menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIBuildingSelectedMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        this.uiViewport = uiViewport;

        // Initialise move button
        moveButton = new TextButton("Move", skin);
        // todo allow buildings to move

        // Initialise sell button
        sellButton = new TextButton("Sell", skin);
        // todo allow selling buildings

        // Place buttons onto table
        table.add(moveButton);
        table.add(sellButton).padLeft(2);

        // Style and place the table
        table.setSize(150, 50);
        table.setBackground(skin.getDrawable("panel1"));
        placeTable();

        // Initially close the menu
        closeMenu();

        // Open menu when receiving an event to do so
        EventHandler.getEventHandler().createEvent("open_selected_menu", (params) -> {
            openMenu();
            return null;
        });

        // Close menu when receiving an event to do so
        EventHandler.getEventHandler().createEvent("close_selected_menu", (params) -> {
            closeMenu();
            return null;
        });
    }

    public void render() {
    }

    private void openMenu() {
        Vector2 mouse = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        uiViewport.unproject(mouse);
        table.setPosition(mouse.x, mouse.y);
        // todo keep this placed relative to world space

        table.setVisible(true);
    }

    private void closeMenu() {
        table.setVisible(false);
    }

    @Override
    protected void placeTable() {
        // This doesn't make sense for this element as it will always be positioned by the mouse cursor
    }
}

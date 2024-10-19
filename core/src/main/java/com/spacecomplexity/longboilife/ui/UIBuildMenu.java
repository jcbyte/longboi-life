package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.building.BuildingCategory;

/**
 * Class to represent the Build Menu UI.
 */
public class UIBuildMenu extends UIElement {
    /**
     * Initialise build menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIBuildMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        closeBuildMenu();
        placeTable();
    }

    public void render() {
        // todo
    }

    /**
     * Open the build menu ready for the user to place buildings from a specific {@link BuildingCategory}.
     *
     * @param category specific category of buildings to show.
     */
    public void openBuildMenu(BuildingCategory category) {
        // todo create the building buttons
        table.setVisible(true);
    }

    /**
     * Close the build menu.
     */
    public void closeBuildMenu() {
        table.setVisible(false);
    }

    @Override
    protected void placeTable() {
        table.setSize(uiViewport.getWorldWidth(), 150);
        table.setPosition(0, 55);
    }
}

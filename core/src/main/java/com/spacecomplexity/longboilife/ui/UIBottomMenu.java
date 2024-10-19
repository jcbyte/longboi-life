package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.building.BuildingCategory;

import java.util.Arrays;

/**
 * Class to represent the Bottom Menu UI.
 */
public class UIBottomMenu extends UIElement {
    private TextButton[] buildingButtons;
    private TextButton pauseButton;

    /**
     * Initialise bottom menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIBottomMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        // Initialise building buttons
        buildingButtons = Arrays.stream(BuildingCategory.values()).map(
            (BuildingCategory category) -> new TextButton(category.getDisplayName(), skin)
        ).toArray(TextButton[]::new);

        // Place building buttons on separate table for condensed styling
        Table buildingButtonsTable = new Table(skin);
        for (TextButton button : buildingButtons) {
            buildingButtonsTable.add(button).expandX().padLeft(2);
        }
        // Add the buttons onto the main table
        table.add(buildingButtonsTable).expandX().left();

        // Initialise pause button
        // todo display ❚❚ ▶
        pauseButton = new TextButton("❚❚", skin);
        // Place pause button on the table
        table.add(pauseButton).right().padRight(2);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        placeTable();
    }

    public void render() {
        // todo
    }

    @Override
    protected void placeTable() {
        table.setSize(uiViewport.getWorldWidth(), 60);
        table.setPosition(0, 0);
    }
}

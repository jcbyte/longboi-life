package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.EventHandler;
import com.spacecomplexity.longboilife.building.BuildingCategory;
import com.spacecomplexity.longboilife.building.BuildingType;

/**
 * Class to represent the Build Menu UI.
 */
public class UIBuildMenu extends UIElement {
    private Skin skin;

    /**
     * Initialise build menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIBuildMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        this.skin = skin;

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        closeBuildMenu();
        placeTable();

        // Close build menu when receiving an event to do so.
        EventHandler.getEventHandler().createEvent("close_build_menu", (params) -> {
            closeBuildMenu();
            return null;
        });
    }

    public void render() {
    }

    /**
     * Open the build menu ready for the user to place buildings from a specific {@link BuildingCategory}.
     *
     * @param category specific category of buildings to show.
     */
    public void openBuildMenu(BuildingCategory category) {
        // CLear previous buildings from the table
        table.clear();

        // Get list of all buildings to display on this menu
        BuildingType[] buildings = BuildingType.getBuildingsOfType(category);

        // Place building buttons on separate table for condensed styling
        Table buildingButtonsTable = new Table();
        for (BuildingType building : buildings) {
            // Get building texture and make it fill the bar
            TextureRegionDrawable texture = new TextureRegionDrawable(building.getTexture());
            float textureSize = table.getHeight() - 60;
            texture.setMinSize(textureSize, textureSize);

            // Initialise building button
            ImageButton button = new ImageButton(texture);
            // Initialise place building sequence when clicked
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    try {
                        EventHandler.getEventHandler().callEvent("start_building", building);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }

                    closeBuildMenu();
                }
            });

            // Initialise building labels
            Label titleLabel = new Label(building.name(), skin);
            Label costLabel = new Label(String.format("$%.2f", building.getCost()), skin);

            // create container for UI elements relating to this building
            Table buildingTable = new Table();
            // Add building UI elements into there container
            buildingTable.add(button);
            buildingTable.row();
            buildingTable.add(titleLabel).padTop(2);
            buildingTable.row();
            buildingTable.add(costLabel).padTop(2);

            // Add the container to the building table
            buildingButtonsTable.add(buildingTable).expandX().expandY().fillY().padLeft(2);
        }
        // Add the buildings table onto the main table
        table.add(buildingButtonsTable).expandX().left();

        // Show the build menu
        table.setVisible(true);
    }

    /**
     * Close the build menu.
     */
    public void closeBuildMenu() {
        // Hide the build menu
        table.setVisible(false);
    }

    @Override
    protected void placeTable() {
        table.setSize(uiViewport.getWorldWidth(), 150);
        table.setPosition(0, 55);
    }
}

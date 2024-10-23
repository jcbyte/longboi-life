package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.EventHandler;
import com.spacecomplexity.longboilife.TimerManager;
import com.spacecomplexity.longboilife.building.BuildingCategory;

/**
 * Class to represent the Bottom Menu UI.
 */
public class UIBottomMenu extends UIElement {
    private TextButton pauseButton;
    private UIBuildMenu buildMenu;

    /**
     * Initialise bottom menu elements.
     *
     * @param uiViewport  the viewport used to render UI.
     * @param parentTable the table to render this element onto.
     * @param skin        the provided skin.
     */
    public UIBottomMenu(Viewport uiViewport, Table parentTable, Skin skin) {
        super(uiViewport, parentTable, skin);

        buildMenu = new UIBuildMenu(uiViewport, parentTable, skin);

        // Place building buttons on separate table for condensed styling
        Table buildingButtonsTable = new Table(skin);
        // Initialise building buttons
        for (BuildingCategory category : BuildingCategory.values()) {
            TextButton button = new TextButton(category.getDisplayName(), skin);

            // On click execute function to open the buildMenuTable on the specific category
            button.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    buildMenu.openBuildMenu(category);
                }
            });

            buildingButtonsTable.add(button).expandX().padLeft(2);
        }
        // Add the buttons onto the main table
        table.add(buildingButtonsTable).expandX().left();

        // Initialise pause button
        // todo display ❚❚ ▶
        pauseButton = new TextButton("Pause", skin);
        // Pause the game when clicked
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                TimerManager timerManager = TimerManager.getTimerManager();

                if (timerManager.getTimer().isPaused()) {
                    // If currently paused then resume playing
                    pauseButton.setText("Pause");
                    try {
                        EventHandler.getEventHandler().callEvent("resume_game");
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // If currently playing then pause
                    pauseButton.setText("Play");
                    try {
                        EventHandler.getEventHandler().callEvent("pause_game");
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        // Place pause button on the table
        table.add(pauseButton).right().padRight(2);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        placeTable();
    }

    public void render() {
        buildMenu.render();
    }

    @Override
    public void resize() {
        placeTable();
        buildMenu.resize();
    }

    @Override
    protected void placeTable() {
        table.setSize(uiViewport.getWorldWidth(), 60);
        table.setPosition(0, 0);
    }
}

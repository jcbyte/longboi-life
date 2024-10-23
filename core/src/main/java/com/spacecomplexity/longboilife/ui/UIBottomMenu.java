package com.spacecomplexity.longboilife.ui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.spacecomplexity.longboilife.EventHandler;
import com.spacecomplexity.longboilife.GameState;
import com.spacecomplexity.longboilife.TimerManager;
import com.spacecomplexity.longboilife.building.BuildingCategory;
import com.spacecomplexity.longboilife.utils.UIUtils;

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
        pauseButton = new TextButton("Pause", skin);
        // Pause/resume the game when clicked
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Call the events to pause/resume the game based on the current pause state
                try {
                    EventHandler.getEventHandler().callEvent(GameState.getState().paused ? "resume_game" : "pause_game");
                } catch (NoSuchMethodException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // Place pause button on the table
        table.add(pauseButton).right().padRight(2);

        // Style and place the table
        table.setBackground(skin.getDrawable("panel1"));
        placeTable();

        // Assign pause and play events
        EventHandler.getEventHandler().createEvent("pause_game", (params) -> {
            // Set pause state
            GameState.getState().paused = true;
            // Pause the timer
            TimerManager.getTimerManager().getTimer().pauseTimer();
            // Close menus and deselect any buildings
            buildMenu.closeBuildMenu();
            GameState.getState().selectedBuilding = null;
            // Disable all UI but the pause button
            UIUtils.disableAllActors(parentTable.getStage());
            UIUtils.enableActor(pauseButton);
            // Change text to resume // todo change to ❚❚
            pauseButton.setText("Resume");

            return null;
        });
        EventHandler.getEventHandler().createEvent("resume_game", (params) -> {
            // Set pause state
            GameState.getState().paused = false;
            // Resume the timer
            TimerManager.getTimerManager().getTimer().resumeTimer();
            // Re enable all UI
            UIUtils.enableAllActors(parentTable.getStage());
            // Change text to resume // todo change to ▶
            pauseButton.setText("Pause");

            return null;
        });
    }

    public void render() {
        buildMenu.render();
    }

    @Override
    public void resize() {
        super.resize();
        
        buildMenu.resize();
    }

    @Override
    protected void placeTable() {
        table.setSize(uiViewport.getWorldWidth(), 60);
        table.setPosition(0, 0);
    }
}

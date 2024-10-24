package com.spacecomplexity.longboilife;

import java.util.HashMap;
import java.util.function.Function;

/**
 * CLass to manage events which can be called from anywhere within the game.
 */
public class EventHandler {
    public enum Event {
        BUILD,
        SELECT_BUILDING,
        CANCEL_OPERATIONS,
        SELL_BUILDING,
        MOVE_BUILDING,
        PAUSE_GAME,
        RESUME_GAME,
        OPEN_SELECTED_MENU,
        CLOSE_SELECTED_MENU,
        CLOSE_BUILD_MENU,
        ;
    }

    private static final EventHandler eventHandler = new EventHandler();
    private HashMap<Event, Function<Object[], Object>> events;

    /**
     * Initialise events attributes.
     */
    public EventHandler() {
        events = new HashMap<>();
    }

    /**
     * Create an event.
     *
     * @param event    the enum the event, needed when called.
     * @param callback the event method, this is what will be executed.
     */
    public void createEvent(Event event, Function<Object[], Object> callback) {
        events.put(event, callback);
    }

    /**
     * Call a previously defined event.
     *
     * @param event  the enum of the event defined.
     * @param params the parameter to pass to the event.
     * @return what the original event would return, this will need to be cast as we cannot know the type here.
     * @throws NoSuchMethodException if the event has not been defined.
     */
    public Object callEvent(Event event, Object... params) throws NoSuchMethodException {
        Function<Object[], Object> callback = events.get(event);

        // If the callback is not defined then throw an error
        if (callback == null) {
            throw new NoSuchMethodException("No event defined for: \"" + event.name() + "\"");
        }

        // Execute the callback and return the result
        return callback.apply(params);
    }

    /**
     * Get the singleton instance of the {@link EventHandler} class.
     *
     * @return The single {@link EventHandler} class.
     */
    public static EventHandler getEventHandler() {
        return eventHandler;
    }
}

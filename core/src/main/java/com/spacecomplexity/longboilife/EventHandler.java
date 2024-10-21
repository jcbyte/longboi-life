package com.spacecomplexity.longboilife;

import java.util.HashMap;
import java.util.function.Function;

/**
 * CLass to manage events which can be called from anywhere within the game.
 */
public class EventHandler {
    private static final EventHandler eventHandler = new EventHandler();
    private HashMap<String, Function<Object[], Object>> events;

    /**
     * Initialise events attributes.
     */
    public EventHandler() {
        events = new HashMap<>();
    }

    /**
     * Create an event.
     *
     * @param name  the name of the event, needed when called.
     * @param event the event method, this is what will be executed.
     */
    public void createEvent(String name, Function<Object[], Object> event) {
        events.put(name, event);
    }

    /**
     * Call a previously defined event.
     *
     * @param name   the name of the event defined.
     * @param params the parameter to pass to the event.
     * @return what the original event would return, this will need to be cast as we cannot know the type here.
     * @throws NoSuchMethodException if the event has not been defined.
     */
    public Object callEvent(String name, Object... params) throws NoSuchMethodException {
        Function<Object[], Object> event = events.get(name);

        // If the event is not defined then throw an error
        if (event == null) {
            throw new NoSuchMethodException("No event: \"" + name + "\"");
        }

        // Execute the event and return the result
        return event.apply(params);
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

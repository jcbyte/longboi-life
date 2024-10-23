package com.spacecomplexity.longboilife;

import com.spacecomplexity.longboilife.utils.Timer;

/**
 * Singleton class to contain the global timer used within the game.
 */
public class TimerManager {
    private static final TimerManager timerManager = new TimerManager();

    private Timer timer;

    private TimerManager() {
        timer = new Timer();

        // Assign pause and play events here to pause and unpause the timer as well as setting state
        EventHandler.getEventHandler().createEvent("pause_game", (params) -> {
            timer.pauseTimer();
            GameState.getState().paused = true;
            return null;
        });
        EventHandler.getEventHandler().createEvent("resume_game", (params) -> {
            timer.resumeTimer();
            GameState.getState().paused = false;
            return null;
        });
    }

    /**
     * Get the singleton instance of the {@link TimerManager} class.
     *
     * @return The single {@link TimerManager} class.
     */
    public static TimerManager getTimerManager() {
        return timerManager;
    }

    public Timer getTimer() {
        return timer;
    }
}

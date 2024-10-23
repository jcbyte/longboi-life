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

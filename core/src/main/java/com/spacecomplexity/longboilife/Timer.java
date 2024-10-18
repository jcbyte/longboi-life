package com.spacecomplexity.longboilife;

/**
 * Class to represent a simple timer
 */
public class Timer {
    private long finishTime;
    private long onPauseTime;
    private boolean paused;

    /**
     * Create a new timer with specified duration.
     *
     * @param duration duration of timer in ms.
     */
    Timer(long duration) {
        finishTime = System.currentTimeMillis() + duration;
        paused = false;
    }

    /**
     * Pause the currently running timer.
     */
    public void pauseTimer() {
        onPauseTime = System.currentTimeMillis();
        paused = true;
    }

    /**
     * Resume the paused timer.
     *
     * @throws IllegalStateException if the timer has not been previously paused.
     */
    public void resumeTimer() throws IllegalStateException {
        if (!paused) {
            throw new IllegalStateException("Timer has not been paused");
        }

        // Calculate the time the timer has been paused for and add this onto the finishing time
        long pausedTime = System.currentTimeMillis() - onPauseTime;
        finishTime += pausedTime;

        paused = false;
    }

    /**
     * Retrieve how much time is left for the timer.
     *
     * @return the time left in ms.
     */
    public long getTimeLeft() {
        if (paused) {
            return finishTime - onPauseTime;
        }

        return finishTime - System.currentTimeMillis();
    }
}
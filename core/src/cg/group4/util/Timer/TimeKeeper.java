package cg.group4.util.Timer;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

/**
 * TimeKeeper manages the intervals that the game requires.
 * When a timer has passed it will notify all the listeners to those timers.
 */
public class TimeKeeper {
    /**
     * TAG is used for debugging purposes and contains the name of the class.
     */
    public static final String TAG = TimeKeeper.class.getSimpleName();

    /**
     * Contains the listeners for strollInterval.
     */
    protected List<TimerListener> strollInterval, strollTimer;

    /**
     * Creates a new TimeKeeper.
     */
    public TimeKeeper(){
        Gdx.app.debug(TAG, "New instance created.");
        strollInterval = new ArrayList<TimerListener>();
        strollTimer = new ArrayList<TimerListener>();
    }

    /**
     * Adds a listener to the specified timer.
     * @param timer The timer that we want to be notified about.
     * @param listener The listener that will get notified when the timer passes.
     */
    public void addTimerListener(Timers timer, TimerListener listener) {
        Gdx.app.debug(TAG, "Added a listener for " + timer.name());
        switch (timer) {
            case STROLL_INTERVAL:
                if(!strollInterval.contains(listener)) {
                    strollInterval.add(listener);
                }
                break;
            case STROLL_TIME:
                if(!strollTimer.contains(listener)) {
                    strollTimer.add(listener);
                }
                break;
        }
    }

    /**
     * Returns the number of listeners the stroll interval timer has.
     * @return Number of listeners.
     */
    public int numberOfStrollIntervalListeners(){
        return strollInterval.size();
    }

    /**
     * Return the number of listeners the stroll time timer has.
     * @return Number of listeners.
     */
    public int numberOfStrollTimerListeners(){
        return strollTimer.size();
    }

    /**
     * The different timers that are available.
     */
    public enum Timers{
        /**
         * The length of a stroll in seconds.
         */
        STROLL_TIME(5),
        /**
         * The interval in seconds in which strolls appear.
         */
        STROLL_INTERVAL(60);

        /**
         * The duration of a Timer.
         */
        private int duration;

        /**
         * Construct the timer enum with a duration attached to it.
         * @param duration The duration in minutes.
         */
        Timers(int duration){
            this.duration = duration;
        }

        /**
         * Returns the duration attached to the enum.
         * @return The duration in minutes.
         */
        public int getDuration(){
            return duration;
        }

    }

}

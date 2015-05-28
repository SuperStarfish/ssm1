package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of all the timers throughout the game.
 *
 * @author Benjamin Los
 */
public final class TimerStore {

    /**
     * Singleton of timer store.
     */
    protected static TimerStore cInstance;

    /**
     * Hashmap that contains all the timers.
     */
    protected Map<String, Timer> cTimers;

    /**
     * A reference to the timekeeper of the timers.
     */
    protected TimeKeeper cTimeKeeper;

    /**
     * Class to store timers to be accessed.
     */
    private TimerStore() {
        cTimers = new HashMap<String, Timer>();
        cTimeKeeper = new TimeKeeper();

        for (Timer.Global timer : Timer.Global.values()) {
            addTimer(timer.name(), new Timer(timer.name(), timer.getDuration(), true));
        }
        Gdx.app.debug(TimeKeeper.TAG, "Created a new TimeStore!");
    }

    /**
     * Getter for time store instance.
     *
     * @return cInstance
     */
    public static TimerStore getInstance() {
        if (cInstance == null) {
            cInstance = new TimerStore();
        }
        return cInstance;
    }

    /**
     * Stores the timer in the store under the given name.
     *
     * @param name  Name of the timer.
     * @param timer Timer to be stored.
     */
    public void addTimer(final String name, final Timer timer) {
        cTimers.put(name, timer);
        cTimeKeeper.getTimerSubject().addObserver(timer);
    }

    /**
     * Removes the timer from the store with the given name.
     *
     * @param name Name of the timer to be removed.
     */
    public void removeTimer(final String name) {
        Timer timer = getTimer(name);
        timer.dispose();
        cTimeKeeper.getTimerSubject().deleteObserver(timer);
        cTimers.remove(name);
    }

    /**
     * Returns the timer of the given name from the store.
     *
     * @param name name of the timer to be returned.
     * @return Returns the timer belonging to the given name.
     */
    public Timer getTimer(final String name) {
        return cTimers.get(name);
    }

    /**
     * Getter for the timekeeper that keeps track of all the timers.
     *
     * @return The timekeeper.
     */
    public TimeKeeper getTimeKeeper() {
        return cTimeKeeper;
    }
}

package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps track of all the timers throughout the game.
 */
public final class TimerStore {

    /**
     * Singleton of timer store.
     */
    protected static final TimerStore INSTANCE = new TimerStore();

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
    protected TimerStore() {
        cTimers = new HashMap<String, Timer>();
        cTimeKeeper = new TimeKeeper();

        for (Timer.Global timer : Timer.Global.values()) {
            addTimer(new Timer(timer.name(), timer.getDuration(), true));
        }
        Gdx.app.debug(TimeKeeper.TAG, "Created a new TimeStore!");
    }

    /**
     * Stores the timer in the store under the timers name.
     *
     * @param timer Timer to be stored.
     */
    public void addTimer(final Timer timer) {
        cTimers.put(timer.getName(), timer);
        cTimeKeeper.getTimerSubject().addObserver(timer);
    }

    /**
     * Getter for time store instance.
     *
     * @return INSTANCE
     */
    public static TimerStore getInstance() {
        return INSTANCE;
    }

    /**
     * Removes the timer from the store with the given name.
     *
     * @param timer The timer to be removed.
     */
    public void removeTimer(final Timer timer) {
        timer.dispose();
        cTimeKeeper.getTimerSubject().deleteObserver(timer);
        cTimers.remove(timer.getName());
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

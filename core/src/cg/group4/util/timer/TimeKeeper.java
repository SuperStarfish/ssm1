package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;

import java.util.LinkedHashSet;

/**
 * Singleton timekeeper which keeps track of every individual timer.
 * @author Jurgen van Schagen
 * @author Benjamin Los
 */
public final class TimeKeeper {
	
	/**
	 * Tag used for debugging.
	 */
    public static final String TAG = TimeKeeper.class.getSimpleName();
    
    /**
     * Creates a single instance of the timekeeper. This is a singleton, because of the fact
     * that we want all timers to be tracked by one 'person'.
     */
    private static final TimeKeeper INSTANCE = new TimeKeeper();
    LinkedHashSet<Timer> cTimers;
    private long cPreviousTick;

    protected final long cMillisInSecond = 1000;


    /**
     * Keeps track of the timers and updates them every second.
     */
    private TimeKeeper() {
        cTimers = new LinkedHashSet<Timer>();
        cPreviousTick = System.currentTimeMillis();
        Gdx.app.debug(TimeKeeper.TAG, "Created a new TimeKeeper!");
    }

    /**
     * Method that returns the singleton timekeeper.
     * @return Singleton TimeKeeper
     */
    public static TimeKeeper getInstance() {
        return INSTANCE;
    }

    /**
     * Initializes the global timers (interval and stroll).
     */
    public void init() {
        for (Timer.Global timer : Timer.Global.values()) {
            new Timer(timer.name(), timer.getDuration(), true);
        }
    }

    /**
     * Looks if a second has past since the last update, if so it will update the timers.
     */
    public void update() {
        long timeStamp = System.currentTimeMillis();

        if (timeStamp - cPreviousTick > cMillisInSecond) {
            for (Timer timer : cTimers) {
                timer.tick(timeStamp);
            }
            cPreviousTick = timeStamp;
        }
    }

    /**
     * Adds a timer to the timeKeeper.
     * @param timer Timer to add to be controlled by the timeKeeper
     */
    void addTimer(Timer timer) {
        if (cTimers.add(timer)) {
            Gdx.app.debug(TAG, "Added Timer '" + timer.getName() + "'.");
        }
    }

    /**
     * Returns a timer with the given name if it is controlled by the timeKeeper.
     * @param name Name of a timer
     * @return Returns the timer with the specified name
     */
    public Timer getTimer(String name) {
        for (Timer timer : cTimers) {
            if (timer.getName().equals(name)) {
                return timer;
            }
        }
        return null;
    }
}

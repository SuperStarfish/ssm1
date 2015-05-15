package cg.group4.util.timer;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.Gdx;

/**
 * Singleton TimeKeeper which keeps track of every individual timer.
 * @author Jurgen van Schagen
 * @author Benjamin Los
 */
public final class TimeKeeper {

	/**
	 * Tag used for debugging.
	 */
	public static final String TAG = TimeKeeper.class.getSimpleName();

	/**
	 * Creates a single instance of the TimeKeeper.
	 * This is a singleton, because of the fact
	 * that we want all timers to be tracked by one 'person'.
	 */
	protected static final TimeKeeper INSTANCE = new TimeKeeper();


	/**
	 * Set of all the timers.
	 */
	protected Set<Timer> cTimers;

	/**
	 * Previous tick that the timers were called.
	 */
	protected long cPreviousTick;

	/**
	 * Amount of milliseconds in one second.
	 */
	protected final long cMillisInSecond = 1000;


	/**
	 * Keeps track of the timers and updates them every second.
	 */
	private TimeKeeper() {
		cTimers = new HashSet<Timer>();
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
	 * Looks whether a second has past since the last update.
	 * If so it will update the timers.
	 */
	public void update() {
		long timeStamp = System.currentTimeMillis();

		if (timeStamp - cPreviousTick > cMillisInSecond) {
			for (Timer timer : cTimers) {
				timer.tick(timeStamp);
			}
			cPreviousTick += cMillisInSecond;
		}
	}

	/**
	 * Adds a timer to the timeKeeper.
	 * @param timer Timer to add to be controlled by the timeKeeper
	 */
	void addTimer(final Timer timer) {
		if (cTimers.add(timer)) {
			Gdx.app.debug(TAG, "Added Timer '" + timer.getName() + "'.");
		}
	}

	/**
	 * Returns a timer called `name` if controlled by the TimeKeeper.
	 * @param name Name of a timer
	 * @return Returns the timer with the specified name
	 */
	public Timer getTimer(final String name) {
		for (Timer timer : cTimers) {
			if (timer.getName().equals(name)) {
				return timer;
			}
		}
		return null;
	}
}

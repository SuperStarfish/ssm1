package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;

import java.util.HashSet;
import java.util.Set;

/**
 * Singleton TimeKeeper which keeps track of every individual timer.
 * @author Jurgen van Schagen
 * @author Benjamin Los
 * @author Martijn Gribnau
 */
public final class TimeKeeper {

	/**
	 * Tag used for debugging.
	 */
	public static final String TAG = TimeKeeper.class.getSimpleName();

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
	public TimeKeeper() {
		cTimers = new HashSet<Timer>();
		cPreviousTick = System.currentTimeMillis();
		Gdx.app.debug(TimeKeeper.TAG, "Created a new TimeKeeper!");
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
	 * Afterwards it will call resolve to resolve any conflicts.
	 */
	public void update() {
		long timeStamp = System.currentTimeMillis();

		if (timeStamp - cPreviousTick > cMillisInSecond) {
			for (Timer timer : cTimers) {
				timer.resolve();
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
	 * Removes a timer from the timeKeeper.
	 * @param timer Timer to be removed from the timekeeper
	 */
	void removeTimer(final Timer timer) {
		if (cTimers.remove(timer)) {
			Gdx.app.debug(TAG, "Removed Timer '" + timer.getName() + "'.");
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

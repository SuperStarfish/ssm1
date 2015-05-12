package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of the Timer class. Contains a list of subscribers.
 * The Timer class can only be accessed from the TimeKeeper.
 * This is to enforce the Timer to run in the lifecycle of the game.
 *
 * @author Benjamin Los
 * @author Jurgen van Schagen
 */
class Timer {
    public static final String TAG = Timer.class.getSimpleName();
    protected String c_name;                // required
    protected Set<TimerTask> c_timerTasks;  // always created
    protected int c_duration;               // required
    protected long c_finishTime;            // implicit - depends on c_duration
    protected boolean c_persistent;         // required
    protected Preferences c_preferences;    // implicit - depends on c_persistent
    protected boolean c_running;            // implicit - depends on state


    /**
     * Initializes the new Timer using the {@link #init(String, int, boolean) init} with false.
     *
     * @param name     The name of the new Timer.
     * @param duration The duration this Timer will run in seconds.
     */
    public Timer(String name, int duration) {
        init(name, duration, false);
    }

    /**
     * Initializes the new Timer using the {@link #init(String, int, boolean) init} with false.
     *
     * @param name     The name of the new Timer.
     * @param duration The duration this Timer will run in seconds.
     */
    public Timer(String name, int duration, boolean persistent) {
        init(name, duration, persistent);
    }

    /**
     * Initializes the timer using settings provided as well as some default settings.
     *
     * @param name       The name of the new Timer.
     * @param duration   The duration this Timer will run in seconds.
     * @param persistent Does the timer have to exist after the game is exited?
     */
    protected void init(String name, int duration, boolean persistent) {
        c_name = name;
        c_duration = duration;
        c_timerTasks = new HashSet<TimerTask>();
        c_persistent = persistent;
        c_preferences = Gdx.app.getPreferences("TIMER");
        setFinishTime();
    }

    /**
     * Sets the timer finish time to current time + its duration.
     */
    protected void setFinishTime() {
        if (c_persistent && c_preferences.contains(c_name)) {
            c_finishTime = c_preferences.getLong(c_name);
            System.out.println((c_finishTime - System.currentTimeMillis()) / 1000);
            if (System.currentTimeMillis() > c_finishTime) {
                stop();
            } else {
                c_running = true;
            }
        } else {
            reset();
        }
    }

    /**
     * Returns the name of this timer.
     *
     * @return The name of the timer.
     */
    public String getName() {
        return c_name;
    }

    /**
     * Method called by TimeKeeper whenever a second in the game has passed.
     *
     * @param timeStamp The current time.
     */
    protected void tick(long timeStamp) {
        if (c_running) {
            if (timeStamp > c_finishTime) {
                c_running = false;
                notifyStop();
            } else {
                notifyTick((int) (c_finishTime - timeStamp) / 1000);
            }
        }
    }

    /**
     * Notifies the listeners that a Tick event occurred.
     *
     * @param remainingTime
     */
    protected void notifyTick(int remainingTime) {
        for (TimerTask task : c_timerTasks) {
            task.onTick(remainingTime);
        }
    }

    /**
     * Notifies the listeners that a Stop event occurred.
     */
    protected void notifyStop() {
        for (TimerTask task : c_timerTasks) {
            task.onStop();
        }
    }

    /**
     * Notifies the listeners that a Start event occurred.
     */
    protected void notifyStart() {
        for (TimerTask task : c_timerTasks) {
            task.onStart();
        }
    }

    /**
     * Stops the current timer.
     */
    public void stop() {
        c_preferences.putLong(c_name, System.currentTimeMillis());
        c_preferences.flush();
        c_running = false;
        notifyStop();
    }

    public void reset() {
        resetFinishTime();
        Gdx.app.debug(TAG, "Set " + getName() + "-Timer to finish " + ((c_finishTime - System.currentTimeMillis()) / 1000) + " seconds from now.");
        c_running = true;
        notifyStart();
    }

    protected void resetFinishTime() {
        c_finishTime = System.currentTimeMillis() + c_duration * 1000;
        if (c_persistent) {
            c_preferences.putLong(c_name, c_finishTime);
            c_preferences.flush();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || !(obj instanceof Timer)) && c_name.equals(((Timer) obj).getName());
    }

    @Override
    public int hashCode() {
        return c_name.hashCode();
    }

    public void subscribe(TimerTask task) {
        c_timerTasks.add(task);
        task.setTimer(this);
    }

    /**
     * This enum defines timers that are global, meaning that the timers are created on startup and by default are persistent.
     */
    public enum Global {
        INTERVAL(60 * 60), STROLL(5 * 60);

        private int e_duration;

        Global(int duration) {
            e_duration = duration;
        }

        public int getDuration() {
            return e_duration;
        }
    }
}
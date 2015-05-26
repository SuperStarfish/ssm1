package cg.group4.util.timer;

import cg.group4.util.subscribe.Subject;
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
public class Timer {

    /**
     * Tag used for debugging.
     */
    public static final String TAG = Timer.class.getSimpleName();

    /**
     * The amount of milliseconds in a second.
     */
    protected static final int MILLISEC_IN_SEC = 1000;

    /**
     * Name of the Timer.
     */
    protected String cName;                // required

    /**
     * Set of TimerTasks to be subscriber from this timer.
     */
    protected Set<TimerTask> cSubscribe;  // always created

    /**
     * Set of TimerTasks that are subscribed to this timer.
     */
    protected Set<TimerTask> cTimerTasks;  // always created

    /**
     * Set of TimerTasks to be unsubscriber from this timer.
     */
    protected Set<TimerTask> cUnsubscribe;  // always created

    /**
     * Duration of the timer (in seconds).
     */
    protected int cDuration;               // required

    /**
     * Time stamp on which the Timer has to be finished.
     */
    protected long cFinishTime;            // implicit - depends on cDuration

    /**
     * Option to make the Timer persistent.
     */
    protected boolean cPersistent;         // required

    /**
     * Preference identity, in which the time stamps for the timer are saved.
     */
    protected Preferences cPreferences;    // implicit - depends on cPersistent

    /**
     * State of the timer.
     */
    protected boolean cRunning;            // implicit - depends on state

    /**
     * Time remaining to end of timer.
     */
    protected int cRemainingTime;

    /**
     * Subject that can be subscribed to and updates when the timer starts.
     */
    protected Subject cStartSubject;

    /**
     * Subject that can be subscribed to and updates when the timer ticks.
     */
    protected Subject cTickSubject;

    /**
     * Subject that can be subscribed to and updates when the timer stops.
     */
    protected Subject cStopSubject;


    /**
     * Initializes the new Timer using the {@link #init(String, int, boolean) init} with false.
     *
     * @param name     The name of the new Timer.
     * @param duration The duration this Timer will run in seconds.
     */
    public Timer(final String name, final int duration) {
        init(name, duration, false);
    }

    /**
     * Initializes the new Timer using the {@link #init(String, int, boolean) init} with false.
     *
     * @param name       The name of the new Timer.
     * @param duration   The duration this Timer will run in seconds.
     * @param persistent Does the timer have to exist after exiting the game.
     */
    public Timer(final String name, final int duration, final boolean persistent) {
        init(name, duration, persistent);
    }

    /**
     * Initializes the timer using settings provided as well as some default settings.
     *
     * @param name       The name of the new Timer.
     * @param duration   The duration this Timer will run in seconds.
     * @param persistent Does the timer have to exist after the game is exited?
     */
    protected final void init(final String name, final int duration, final boolean persistent) {
        cName = name;
        cDuration = duration;
        cRemainingTime = duration;
        cSubscribe = new HashSet<TimerTask>();
        cTimerTasks = new HashSet<TimerTask>();
        cUnsubscribe = new HashSet<TimerTask>();

        cStartSubject = new Subject();
        cStopSubject = new Subject();
        cTickSubject = new Subject();

        cPersistent = persistent;
        cPreferences = Gdx.app.getPreferences("TIMER");
        setFinishTime();
        TimeKeeper.getInstance().addTimer(this);
    }

    /**
     * Sets the timer finish time to current time + its duration.
     */
    protected final void setFinishTime() {
        if (cPersistent && cPreferences.contains(cName)) {
            cFinishTime = cPreferences.getLong(cName);
            if (System.currentTimeMillis() > cFinishTime) {
                stop();
            } else {
                cRunning = true;
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
    public final String getName() {
        return cName;
    }

    /**
     * Method called by TimeKeeper whenever a second in the game has passed.
     *
     * @param timeStamp The current time.
     */
    protected void tick(final long timeStamp) {
        if (cRunning) {
            if (timeStamp > cFinishTime) {
                cRunning = false;
                cRemainingTime = 0;
                notifyStop();
                cStopSubject.update();
            } else {
                cRemainingTime = (int) (cFinishTime - timeStamp) / MILLISEC_IN_SEC;
                notifyTick(cRemainingTime);
                cTickSubject.update(cRemainingTime);
            }
        }
    }

    /**
     * Notifies the listeners that a Tick event occurred.
     *
     * @param remainingTime Time until timer finishes
     */
    protected final void notifyTick(final int remainingTime) {
        for (TimerTask task : cTimerTasks) {
            if (task.isActive()) {
                task.onTick(remainingTime);
            }
        }
    }

    /**
     * Notifies the listeners that a Stop event occurred.
     */
    protected final void notifyStop() {
        for (TimerTask task : cTimerTasks) {
            if (task.isActive()) {
                task.onStop();
            }
        }

    }

    /**
     * Notifies the listeners that a Start event occurred.
     */
    protected final void notifyStart() {
        for (TimerTask task : cTimerTasks) {
            if (task.isActive()) {
                task.onStart(cDuration);
            }
        }
    }

    /**
     * Stops the current timer.
     */
    public final void stop() {
        if (cRunning) {
            cPreferences.putLong(cName, System.currentTimeMillis());
            cPreferences.flush();
            cRunning = false;
            notifyStop();
            cStopSubject.update();
        }
    }

    /**
     * Disposes the current timer.
     */
    public final void dispose() {
        stop();
        for (TimerTask task : cTimerTasks) {
            task.dispose();
        }
        cStartSubject.deleteObservers();
        cStopSubject.deleteObservers();
        cTickSubject.deleteObservers();
        TimeKeeper.getInstance().removeTimer(this);
    }

    /**
     * Resets the current timer.
     */
    public final void reset() {
        resetFinishTime();
        Gdx.app.debug(TAG, "Set " + getName()
                + "-Timer to finish " + ((cFinishTime - System.currentTimeMillis()) / MILLISEC_IN_SEC)
                + " seconds from now.");
        cRunning = true;
        notifyStart();
        cStartSubject.update();
    }

    /**
     * Resets the time it should end.
     */
    protected final void resetFinishTime() {
        cFinishTime = System.currentTimeMillis() + cDuration * MILLISEC_IN_SEC;
        if (cPersistent) {
            cPreferences.putLong(cName, cFinishTime);
            cPreferences.flush();
        }
    }

    /**
     * @return Returns whether the timer is running or not.
     */
    public final Boolean isRunning() {
        return cRunning;
    }

    public final int getRemainingTime() {
        return cRemainingTime;
    }

    @Override
    public final boolean equals(final Object obj) {
        return !(obj == null || !(obj instanceof Timer)) && cName.equals(((Timer) obj).getName());
    }

    @Override
    public final int hashCode() {
        return cName.hashCode();
    }

    /**
     * Method that adds a TimerTask to the current subscribers.
     *
     * @param task Which task should be added
     */
    public final void subscribe(final TimerTask task) {
        cSubscribe.add(task);
        task.setTimer(this);
        if (cRunning) {
            task.onTick(cRemainingTime);
        }
    }

    /**
     * Method that removes a TimerTask from the current subscribers.
     *
     * @param task Which task should be removed
     */
    public final void unsubscribe(final TimerTask task) {
        cUnsubscribe.add(task);
        task.setTimer(null);
    }

    public final void resolve() {
        cTimerTasks.removeAll(cUnsubscribe);
        cUnsubscribe.clear();
        cTimerTasks.addAll(cSubscribe);
        cSubscribe.clear();
    }

    /**
     * Getter for the stop subject.
     * @return returns the stop subject of the timer
     */
    public Subject getStopSubject() {
        return cStopSubject;
    }

    /**
     * Getter for the start subject.
     * @return returns the start subject of the timer
     */
    public Subject getStartSubject() {
        return cStartSubject;
    }

    /**
     * Getter for the tick subject.
     * @return returns the tick subject of the timer
     */
    public Subject getTickSubject() {
        return cTickSubject;
    }

    /**
     * This enum defines timers that are global.
     * This means that the timers are created on startup and by default are persistent.
     */
    public enum Global {
        /**
         * Length definition of one interval.
         */
        INTERVAL(60 * 60),

        /**
         * Length definition of one stroll.
         * (5 * 60 seconds = 5 minutes)
         */
        STROLL(5 * 60),


        /**
         * Max length of an event is 1 min (60 seconds).
         */
        EVENT(60);

        /**
         * Duration of the global timer.
         */
        private int eDuration;

        /**
         * Set the duration of a global timer.
         *
         * @param duration The duration in seconds
         */
        Global(final int duration) {
            eDuration = duration;
        }

        /**
         * Get the duration of a global timer.
         *
         * @return The duration in seconds
         */
        public int getDuration() {
            return eDuration;
        }
    }
}
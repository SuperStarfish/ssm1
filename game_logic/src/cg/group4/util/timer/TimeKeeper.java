package cg.group4.util.timer;


import cg.group4.data_structures.subscribe.Subject;
import com.badlogic.gdx.Gdx;

/**
 * Singleton TimeKeeper which keeps track of every individual timer.
 */
public final class TimeKeeper {

    /**
     * Tag used for debugging.
     */
    public static final String TAG = TimeKeeper.class.getSimpleName();

    /**
     * Amount of milliseconds in one second.
     */
    protected final long cMillisInSecond = 1000;

    /**
     * Previous tick that the timers were called.
     */
    protected long cPreviousTick;

    /**
     * Subject for a timer to subscribe to to be informed every second.
     */
    protected Subject cTimerSubject;


    /**
     * Keeps track of the timers and updates them every second.
     */
    protected TimeKeeper() {
        cPreviousTick = System.currentTimeMillis();
        cTimerSubject = new Subject();
        Gdx.app.debug(TimeKeeper.TAG, "Created a new TimeKeeper!");
    }

    /**
     * Looks whether a second has past since the last update.
     * If so it will update the timers.
     * Afterwards it will call resolve to resolve any conflicts.
     */
    public void update() {
        long timeStamp = System.currentTimeMillis();

        if (timeStamp - cPreviousTick > cMillisInSecond) {
            cTimerSubject.update(timeStamp);
            cPreviousTick += cMillisInSecond;
        }
    }

    /**
     * Subject that will inform all of its subscribers every second.
     *
     * @return The Subject.
     */
    public Subject getTimerSubject() {
        return cTimerSubject;
    }
}

package cg.group4.game_logic;

import cg.group4.stroll.Stroll;
import cg.group4.util.subscribe.Subject;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.Timer;
import com.badlogic.gdx.Gdx;

/**
 * Class which handles the game logic.
 *
 * @author Martijn Gribnau
 * @author Benjamin Los
 */
public class StandUp {

    /**
     * Tag of this class.
     * Usage: Useful in conjunction with the Logger.
     */
    public static final String TAG = StandUp.class.getSimpleName();

    /**
     * Singleton of game logic handler.
     */
    protected static final StandUp cInstance = new StandUp();

    /**
     * Stroll logic.
     */
    protected Stroll cStroll;

    /**
     * Subject for all the game logic to subscribe to that has to be updated every render cycle.
     */
    protected Subject cUpdateSubject;

    /**
     * Subject for new stroll.
     */
    protected Subject cNewStrollSubject;

    /**
     * Instantiate StandUp and TimeKeeper.
     */
    private StandUp() {
        cUpdateSubject = new Subject();
        cNewStrollSubject = new Subject();
    }

    /**
     * Starts a new stroll.
     */
    public void startStroll() {
        if (cStroll == null) {
        	Gdx.app.log(TAG, "Starting up stroll, created new one.");
            TimeKeeper.getInstance().getTimer(Timer.Global.INTERVAL.name()).reset();
            cStroll = new Stroll();
            cNewStrollSubject.update(null);
        }
    }

    /**
     * Ends the current stroll.
     * @param cRewards rewards gained by the stroll.
     */
    public void endStroll(final int cRewards) {
    	Gdx.app.log(TAG, "Ending stroll");
        cStroll = null;
    }

    /**
     * Getter for StandUp instance.
     * @return cInstance
     */
    public static StandUp getInstance() {
        return cInstance;
    }


    /**
     * Getter for Stroll.
     * @return cStroll
     */
    public Stroll getStroll() {
        return cStroll;
    }

    /**
     * Updates all the game mechanics.
     */
    public void update() {
        cUpdateSubject.update(null);
    }

    /**
     * Getter for the subject to subscribe to to get updated every render cycle.
     * @return Subject to subscribe to.
     */
    public Subject getUpdateSubject() {
        return cUpdateSubject;
    }

    /**
     * Getter for the subject to subscribe to to get updated for new stroll.
     * @return Subject to subscribe to.
     */
    public Subject getNewStrollSubject() {
        return cNewStrollSubject;
    }
}
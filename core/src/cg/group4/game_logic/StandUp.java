package cg.group4.game_logic;

import cg.group4.stroll.Stroll;
import cg.group4.util.subscribe.Subject;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.Timer;
import com.badlogic.gdx.Gdx;

import java.util.Observable;

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
     * Subject for all the game mechanics to subscribe to.
     */
    protected Subject cGameMechanics;

    /**
     * Instantiate StandUp and TimeKeeper.
     */
    private StandUp() {
        cGameMechanics = new Subject();
    }

    /**
     * Starts a new stroll.
     */
    public void startStroll() {
        if (cStroll == null) {
        	Gdx.app.log(TAG, "Starting up stroll, created new one.");
            TimeKeeper.getInstance().getTimer(Timer.Global.INTERVAL.name()).reset();
            cStroll = new Stroll();
        } else {
        	Gdx.app.log(TAG, "Starting up stroll, found old one so resuming.");
            cStroll.resume();
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
        cGameMechanics.update();
    }

    public Observable getGameMechanicSubject() {
        return cGameMechanics;
    }
}
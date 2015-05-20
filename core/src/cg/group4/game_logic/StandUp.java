package cg.group4.game_logic;

import cg.group4.stroll.Stroll;
import cg.group4.util.timer.TimeKeeper;

import java.util.HashSet;
import java.util.Set;

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
     * TimeKeeper logic.
     */
    protected TimeKeeper cTimeKeeper;

    /**
     * list of all the subscribed game mechanics.
     */
    protected Set<GameMechanic> cGameMechanics;

    /**
     * list of all the game mechanics that need to be added.
     */
    protected Set<GameMechanic> cSubscribersGameMechanics;
    /**
     * list of all the game mechanics that need to be removed.
     */
    protected Set<GameMechanic> cUnsubscribersGameMechanics;

    /**
     * Instantiate StandUp and TimeKeeper.
     */
    private StandUp() {
        cTimeKeeper = new TimeKeeper();
        cGameMechanics = new HashSet<GameMechanic>();
        cSubscribersGameMechanics = new HashSet<GameMechanic>();
        cUnsubscribersGameMechanics = new HashSet<GameMechanic>();
    }

    /**
     * Initialize TimeKeeper.
     * This is kept from the constructor of the StandUp, because at construction time of StandUp,
     * the TimeKeeper is not yet constructed.
     */
    public void init() {
        cTimeKeeper.init();
    }

    /**
     * Starts a new stroll.
     */
    public void startStroll() {
        if (cStroll == null) {
        	Gdx.app.log(TAG, "Starting up stroll, created new one.");
            cTimeKeeper.getTimer("INTERVAL").reset();
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
     * Getter for TimeKeeper.
     * @return cTimeKeeper
     */
    public TimeKeeper getTimeKeeper() {
        return cTimeKeeper;
    }

    /**
     * Getter for Stroll.
     * @return cStroll
     */
    public Stroll getStroll() {
        return cStroll;
    }

    /**
     * Goes over all the subscribed game machenics and updates them.
     */
    public void updateGameMechanics() {
        cGameMechanics.removeAll(cUnsubscribersGameMechanics);
        cGameMechanics.addAll(cSubscribersGameMechanics);
        cUnsubscribersGameMechanics.clear();
        cSubscribersGameMechanics.clear();

        for(GameMechanic gm : cGameMechanics) {
            gm.update();
        }

    }

    public void subscribe(GameMechanic gameMechanic) {
        cSubscribersGameMechanics.add(gameMechanic);
    }

    public void unSubscribe(GameMechanic gameMechanic) {
        cUnsubscribersGameMechanics.add(gameMechanic);
    }
}
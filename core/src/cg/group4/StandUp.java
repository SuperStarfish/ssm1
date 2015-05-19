package cg.group4;

import cg.group4.stroll.Stroll;
import cg.group4.util.timer.TimeKeeper;

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
     * Instantiate StandUp and TimeKeeper.
     */
    private StandUp() {
        cTimeKeeper = new TimeKeeper();
    }

    /**
     * Initialize TimeKeeper.
     * This is kept from the constructor of the StandUp, because at construction time of StandUp,
     * the TimeKeeper is not yet constructed.
     */
    public void init(){
        cTimeKeeper.init();
    }


    public void startStroll(){
        cTimeKeeper.getTimer("INTERVAL").reset();
        cStroll = new Stroll();
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
}

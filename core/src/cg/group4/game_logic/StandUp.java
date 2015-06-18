package cg.group4.game_logic;

import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.subscribe.Subject;
import cg.group4.game_logic.stroll.Stroll;
import cg.group4.util.orientation.Orientation;
import cg.group4.util.orientation.OrientationReader;
import cg.group4.util.sensor.AccelerationStatus;
import cg.group4.util.sensor.SensorReader;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import com.badlogic.gdx.Gdx;

/**
 * Class which handles the game logic.
 *
 * @author Martijn Gribnau
 * @author Benjamin Los
 */
public final class StandUp {

    /**
     * Tag of this class.
     * Usage: Useful in conjunction with the Logger.
     */
    public static final String TAG = StandUp.class.getSimpleName();

    /**
     * Singleton of game logic handler.
     */
    protected static StandUp cInstance;

    /**
     * Player of the game.
     */
    protected Player cPlayer;

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
     * Reads sensor input of the device.
     */
    protected SensorReader cSensorReader;

    /**
     * Keeps track of the amount of movement during the game.
     */
    protected AccelerationStatus cAccelerationStatus;
    
    /**
     * Reads the current orientation of the device.
     */
    protected OrientationReader cOrientationReader;

    /**
     * Instantiate StandUp and TimeKeeper.
     */
    protected StandUp() {
        cUpdateSubject = new Subject();
        cNewStrollSubject = new Subject();
        cSensorReader = new SensorReader();
        cPlayer = new Player();
    }

    /**
     * Getter for StandUp instance.
     *
     * @return cInstance
     */
    public static StandUp getInstance() {
        if (cInstance == null) {
            cInstance = new StandUp();
        }
        return cInstance;
    }

    /**
     * Starts a new stroll.
     */
    public void startStroll() {
        if (cStroll == null) {
            Gdx.app.log(TAG, "Starting up stroll, created new one.");
            TimerStore.getInstance().getTimer(Timer.Global.INTERVAL.name()).reset();
            cStroll = new Stroll();
            cNewStrollSubject.update(null);
        }
    }

    /**
     * Ends the current stroll.
     * @param rewardsCollection rewards gained by the stroll.
     */
    public void endStroll(final Collection rewardsCollection) {
        Gdx.app.log(TAG, "Ending stroll");
        cStroll = null;

        cPlayer.getCollection().addAll(rewardsCollection);
    }

    /**
     * Getter for Stroll.
     *
     * @return cStroll
     */
    public Stroll getStroll() {
        return cStroll;
    }

    /**
     * Getter for Player.
     *
     * @return The player of the game.
     */
    public Player getPlayer() {
        return cPlayer;
    }

    /**
     * Updates all the game mechanics.
     */
    public void update() {
        cUpdateSubject.update(null);
    }

    /**
     * Getter for the subject to subscribe to to get updated every render cycle.
     *
     * @return Subject to subscribe to.
     */
    public Subject getUpdateSubject() {
        return cUpdateSubject;
    }

    /**
     * Getter for the subject to subscribe to to get updated for new stroll.
     *
     * @return Subject to subscribe to.
     */
    public Subject getNewStrollSubject() {
        return cNewStrollSubject;
    }

    /**
     * Getter for the SensorReader to read the sensor values of the device.
     *
     * @return SensorReader Object
     */
    public SensorReader getSensorReader() {
        return cSensorReader;
    }

    public void setAccelerationStatus(AccelerationStatus status) {
        cAccelerationStatus = status;
    }

    public AccelerationStatus getAccelerationStatus() {
        return cAccelerationStatus;
    }
    
    /**
     * Set the orientationReader.
     * @param reader OrientationReader that can read the device's current orientation.
     */
    public void setOrientationReader(OrientationReader reader) {
    	cOrientationReader = reader;
    }
    
    /**
     * Get the current orientation of the device.
     * @return Orientation object representing the current orientation of the device.
     * Either Landscape, portrait, or null if the orientation is undefined.
     */
    public Orientation getOrientation() {
    	return cOrientationReader.getOrientation();
    }
}
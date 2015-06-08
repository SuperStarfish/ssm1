package cg.group4.game_logic;

import java.util.ArrayList;

import cg.group4.game_logic.stroll.Stroll;
import cg.group4.rewards.RewardGenerator;
import cg.group4.rewards.collectibles.Collectible;
import cg.group4.util.audio.AudioPlayer;
import cg.group4.util.sensors.SensorReader;
import cg.group4.util.subscribe.Subject;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

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
    protected static StandUp instance = new StandUp();

    /**
     * Background music to be played.
     */
    protected Music cBackgroundMusic;

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
     * Generates the rewards.
     */
    protected RewardGenerator cGenerator;

    /**
     * Instantiate StandUp and TimeKeeper.
     */
    protected StandUp() {
        cUpdateSubject = new Subject();
        cNewStrollSubject = new Subject();
        cSensorReader = new SensorReader();
        cGenerator = new RewardGenerator();

        cBackgroundMusic =  Gdx.audio.newMusic(Gdx.files.internal("music/Summer Day.mp3"));
        cBackgroundMusic.setLooping(true);
        AudioPlayer.getInstance().setLastPlayed(cBackgroundMusic);
        AudioPlayer.getInstance().playAudio(cBackgroundMusic);
    }

    /**
     * Getter for StandUp instance.
     *
     * @return INSTANCE
     */
    public static StandUp getInstance() {
        return instance;
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
     *
     * @param cRewards rewards gained by the stroll.
     */
    public void endStroll(final ArrayList<Integer> cRewards) {
        Gdx.app.log(TAG, "Ending stroll");
        cStroll = null;
        
        for (int score : cRewards) {
        	Collectible c = cGenerator.generateCollectible(score);
        	//ADD COLLECTIBLE TO COLLECTION HERE
        }
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

    public Music getBackGroundMusic() {
        return cBackgroundMusic;
    }
}
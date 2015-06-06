package cg.group4.game_logic.stroll;

import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.game_logic.stroll.events.TestStrollEvent;
import cg.group4.game_logic.stroll.events.fishevent.FishingStrollEvent;
import cg.group4.util.sensors.AccelerationState;
import cg.group4.util.sensors.AccelerationStatus;
import cg.group4.util.subscribe.Subject;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import com.badlogic.gdx.Gdx;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * @author Martijn Gribnau
 * @author Benjamin Los
 */
public class Stroll implements Observer {

    /**
     * The base threshold used for generating events.
     */
    protected static final double BASE_THRESHOLD = 0.002;

    /**
     * Tag used for debugging.
     */
    private static final String TAG = Stroll.class.getSimpleName();

    protected double cEventThreshold;
    /**
     * Amount of rewards collected.
     */
    protected int cRewards;
    /**
     * Whether or not you're busy with an event.
     */
    protected boolean cEventGoing;
    /**
     * Whether the stroll has ended or not.
     */
    protected Boolean cFinished;
    /**
     * The current event being played.
     */
    protected StrollEvent cEvent;
    /**
     * Subject for end of stroll.
     */
    protected Subject cEndStrollSubject;
    /**
     * The stroll timer.
     */
    protected Timer cStrollTimer;

    /**
     * The observer to subscribe to the stop subject of stroll timer.
     */
    protected Observer cStrollStopObserver = new Observer() {

        @Override
        public void update(final Observable o, final Object arg) {
            cFinished = true;
            if (!cEventGoing) {
                done();
            }
        }
    };

    protected Observer cUpdateMovementObserver = new Observer() {

        @Override
        public void update(final Observable o, final Object arg) {
            cEventThreshold = BASE_THRESHOLD * getAmplifier((AccelerationState) arg);
        }
    };

    /**
     * Subject for new event.
     */
    protected Subject cNewEventSubject;

    /**
     * Subject for end of event.
     */
    protected Subject cEndEventSubject;

    /**
     * Constructor, creates a new Stroll object.
     */
    public Stroll(AccelerationStatus status) {
        Gdx.app.log(TAG, "Started new stroll");
        cRewards = 0;
        cEventGoing = false;
        cEventThreshold = BASE_THRESHOLD;
        cFinished = false;
        cEndStrollSubject = new Subject();
        cNewEventSubject = new Subject();
        cEndEventSubject = new Subject();

        StandUp.getInstance().getUpdateSubject().addObserver(this);

        status.getSubject().addObserver(cUpdateMovementObserver);

        cStrollTimer = TimerStore.getInstance().getTimer(Timer.Global.STROLL.name());
        cStrollTimer.getStopSubject().addObserver(cStrollStopObserver);

        cStrollTimer.reset();


    }


    @Override
    public final void update(final Observable o, final Object arg) {
        if (!cEventGoing) {
            generatePossibleEvent();
        }
    }

    /**
     * Generate an event on a certain requirement (e.g. a random r: float < 0.1).
     */
    protected final void generatePossibleEvent() {
        Random rnd = new Random();
        if (rnd.nextDouble() < cEventThreshold) {
            cEventGoing = true;
            int chosenEvent = rnd.nextInt(2);
            switch(chosenEvent) {
                case(0):
                    cEvent = new FishingStrollEvent();
                    break;
                case(1):
                    cEvent = new TestStrollEvent();
                    break;
                default:
                    cEvent = new TestStrollEvent();
            }
            cNewEventSubject.update(cEvent);
        }
    }

    /**
     * Handles completion of an event.
     *
     * @param rewards Reward(s) given on completion of an event
     */
    public final void eventFinished(final int rewards) {
        Gdx.app.log(TAG, "Event completed! Collected " + rewards + " rewards.");

        cEndEventSubject.update(rewards);

        cRewards += rewards;
        cEvent = null;
        cEventGoing = false;

        if (cFinished) {
            Gdx.app.log(TAG, "Event finished and time is up, ending stroll.");
            done();
        }
    }

    /**
     * Method that returns the amplifier for the event chance.
     * @param state Movement state gotten from the AccelLib library.
     * @return Integer used to amplify the chance of getting the event.
     */
    protected int getAmplifier(final AccelerationState state) {
        for (Amplifier a : Amplifier.values()) {
            if (a.cState == state) {
                return a.cAmplifier;
            }
        }
        return 0;
    }


    /**
     * Method that gets called when the stroll has ended/completed.
     */
    public final void done() {
        Gdx.app.log(TAG, "Stroll has ended. Collected " + cRewards + " rewards.");


        StandUp.getInstance().getUpdateSubject().deleteObserver(this);

        cEndStrollSubject.update(cRewards);
        cEndStrollSubject.deleteObservers();

        cStrollTimer.getStopSubject().deleteObserver(cStrollStopObserver);

        StandUp.getInstance().endStroll();
    }

    /**
     * Getter for the subject to subscribe to to get updated for the end of the stroll.
     *
     * @return Subject to subscribe to.
     */
    public final Subject getEndStrollSubject() {
        return cEndStrollSubject;
    }

    /**
     * Getter for the subject to subscribe to get updated for the start of a new event.
     *
     * @return Subject to subscribe to.
     */
    public final Subject getNewEventSubject(){
        return cNewEventSubject;
    }

    /**
     * Getter for the subject to subscribe to to get updated for the end of the event.
     * @return Subject to subscribe to.
     */
    public final Subject getEndEventSubject() {
        return cEndEventSubject;
    }

    /**
     * Amplifier enum.
     */
    public enum Amplifier {
    	
    	/**
    	 * When walking, you should get normal chance of an event.
    	 */
        WALK(AccelerationState.WALKING, 1),
        
        /**
    	 * When running, you should get double chance of an event.
    	 */
        RUN(AccelerationState.RUNNING, 2),
        
        /**
    	 * When not moving at all, you should get no event.
    	 */
        STOP(AccelerationState.RESTING, 0),
        
        /**
    	 * When cheating movements, you should get no events as well.
    	 */
        CHEAT(AccelerationState.CHEATING, 0);

        /**
         * The state of movement.
         */
        private AccelerationState cState;

        /**
         * Amplifier for the movement.
         */
        private int cAmplifier;

        /**
         * Constructor for the Enum.
         * @param state The state of movement.
         * @param amplifier Amplifier for the movement.
         */
        Amplifier(final AccelerationState state, final int amplifier) {
            this.cAmplifier = amplifier;
            this.cState = state;
        }
    }
}

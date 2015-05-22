package cg.group4.stroll;

import cg.group4.game_logic.StandUp;
import cg.group4.stroll.events.StrollEvent;
import cg.group4.stroll.events.TestStrollEvent;
import cg.group4.util.subscribe.Subject;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.TimerTask;
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
    /**
     * Amount of rewards collected.
     */
    protected int cRewards;
    /**
     * The chance of an event happening this second.
     */
    protected double cEventThreshold;
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
     * The timer task to listen to the stroll timer.
     */
    protected final TimerTask cTimerTask = new TimerTask() {
        @Override
        public void onTick(final int seconds) {
        }

        @Override
        public void onStart(final int seconds) {
            cFinished = false;
        }

        @Override
        public void onStop() {
            cFinished = true;
            if (!cEventGoing) {
                done();
            }
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
    public Stroll() {
        Gdx.app.log(TAG, "Started new stroll");
        cRewards = 0;
        cEventGoing = false;
        cFinished = false;
        cEventThreshold = BASE_THRESHOLD;
        cEndStrollSubject = new Subject();
        cNewEventSubject = new Subject();
        cEndEventSubject = new Subject();

        StandUp.getInstance().getUpdateSubject().addObserver(this);

        TimeKeeper.getInstance().getTimer("STROLL").subscribe(cTimerTask);
        cTimerTask.getTimer().reset();
    }

    /**
     * Every cycle, as long as there is no event going on, we want to generate an event.
     */
    @Override
    public final void update(Observable o, Object arg) {
        if (!cEventGoing) {
            generatePossibleEvent();
        }
    }

    /**
     * Generate an event on a certain requirement (e.g. a random r: float < 0.1).
     */
    protected final void generatePossibleEvent() {
        Random rnd = new Random();
        if (rnd.nextFloat() < cEventThreshold) {
            cEventGoing = true;
            cEvent = new TestStrollEvent();
            cNewEventSubject.update(cEvent);
        }
    }

    /**
     * Handles completion of an event.
     *
     * @param rewards Reward(s) given on completion of an event
     */
    public final void eventFinished(final int rewards) {
        Gdx.app.log(TAG, "Event completed!");

        cEndEventSubject.update(null);

        cRewards += rewards;
        cEvent = null;
        cEventGoing = false;

        if (cFinished) {
            Gdx.app.log(TAG, "Event finished and time is up, ending stroll.");
            done();
        }
    }


    /**
     * Method that gets called when the stroll has ended/completed.
     */
    public final void done() {
        Gdx.app.log(TAG, "Stroll has ended.");
        cTimerTask.dispose();

        StandUp.getInstance().getUpdateSubject().deleteObserver(this);

        cEndStrollSubject.update(null);
        cEndStrollSubject.deleteObservers();

        StandUp.getInstance().endStroll(cRewards);
    }

    /**
     * Getter for the subject to subscribe to to get updated for the end of the stroll.
     *
     * @return Subject to subscribe to.
     */
    public Subject getEndStrollSubject() {
        return cEndStrollSubject;
    }

    /**
     * Getter for the subject to subscribe to get updated for the start of a new event.
     *
     * @return Subject to subscribe to.
     */
    public Subject getNewEventSubject() {
        return cNewEventSubject;
    }

    /**
     * Getter for the subject to subscribe to to get updated for the end of the event.
     *
     * @return Subject to subscribe to.
     */
    public Subject getEndEventSubject() {
        return cEndEventSubject;
    }
}

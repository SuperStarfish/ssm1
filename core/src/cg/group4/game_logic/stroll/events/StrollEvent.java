package cg.group4.game_logic.stroll.events;

import cg.group4.data_structures.subscribe.Subject;
import cg.group4.game_logic.StandUp;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;

import java.util.Observable;
import java.util.Observer;

/**
 * Interface that gets implemented by every event.
 *
 * @author Nick Cleintuar
 * @author Benjamin Los
 * @author Martijn Gribnau
 */
public abstract class StrollEvent implements Disposable, Observer {

    /**
     * Timer to constrain the amount of time spent on an event.
     */
    protected final Observer cEventStopObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            clearEvent();
        }
    };
    /**
     * Every strollEvent has a respective timer.
     */
    protected Timer cEventTimer;
    /**
     * Subject to detect event changes.
     */
    protected Subject cDataSubject;

    /**
     * Constructor, creates a new stroll event.
     */
    public StrollEvent() {
        cDataSubject = new Subject();

        Gdx.app.log(this.getClass().getSimpleName(), "Event started!");
        StandUp.getInstance().getUpdateSubject().addObserver(this);

        cEventTimer = TimerStore.getInstance().getTimer(Timer.Global.EVENT.name());
        cEventTimer.getStopSubject().addObserver(cEventStopObserver);
        cEventTimer.reset();
    }

    /**
     * Returns the reward accumulated by completing the event.
     *
     * @return the reward.
     */
    public abstract int getReward();

    /**
     * Cleanup after the event.
     */
    protected abstract void clearEvent();

    /**
     * Starts the event.
     */
    public abstract void start();

    /**
     * Getter for the event subject. Detects event changes.
     *
     * @return The event data subject.
     */
    public Subject getEventChangeSubject() {
        return cDataSubject;
    }

    /**
     * Method that gets called to dispose of the event.
     */
    public final void dispose() {
        StandUp.getInstance().getUpdateSubject().deleteObserver(this);
        Gdx.app.log(this.getClass().getSimpleName(), "Event completed!");
        cEventTimer.getStopSubject().deleteObserver(cEventStopObserver);
        cEventTimer.dispose();
        StandUp.getInstance().getStroll().eventFinished(getReward());
    }
}

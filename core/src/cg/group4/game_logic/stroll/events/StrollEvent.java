package cg.group4.game_logic.stroll.events;

import cg.group4.game_logic.StandUp;
import cg.group4.util.subscribe.Subject;
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
     * Every strollEvent has a respective timer.
     */
    protected Timer cEventTimer;

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
     * Subject to detect label changes.
     */
    protected Subject cLabelSubject;

    /**
     * Constructor, creates a new stroll event.
     */
    public StrollEvent() {
        cLabelSubject = new Subject();

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
     * Getter for the label subject. Detects label changes.
     *
     * @return The label subject.
     */
    public Subject getLabelSubject() {
        return cLabelSubject;
    }

    /**
     * Method that gets called to dispose of the event.
     */
    public final void dispose() {
        StandUp.getInstance().getUpdateSubject().deleteObserver(this);
        Gdx.app.log(this.getClass().getSimpleName(), "Event completed!");
        cEventTimer.getStopSubject().deleteObserver(cEventStopObserver);
        cEventTimer.stop();
        StandUp.getInstance().getStroll().eventFinished(getReward());
    }
}

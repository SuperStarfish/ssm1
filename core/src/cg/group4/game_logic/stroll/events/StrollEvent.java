package cg.group4.game_logic.stroll.events;

import cg.group4.data_structures.subscribe.Subject;
import cg.group4.game_logic.StandUp;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import cg.group4.view.screen.EventScreen;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
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
public abstract class StrollEvent extends InputAdapter implements Disposable, Observer, InputProcessor {

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

    protected InputProcessor cProcessor;

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

        cProcessor = Gdx.input.getInputProcessor();
        if(cProcessor instanceof InputMultiplexer) {
            ((InputMultiplexer)cProcessor).addProcessor(this);
        }
    }

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
     * Calls dispose using true, helper method for clearing events.
     */
    public final void dispose() {
        dispose(true);
    }

    /**
     * Method that gets called to dispose of the event.
     * @param eventCompleted If the event is completed or not.
     */
    public final void dispose(boolean eventCompleted) {
        StandUp.getInstance().getUpdateSubject().deleteObserver(this);
        Gdx.app.log(this.getClass().getSimpleName(), "Event completed!");
        cEventTimer.getStopSubject().deleteObserver(cEventStopObserver);
        cEventTimer.dispose();
        int reward = 0;
        if (eventCompleted) {
            reward = getReward();
        }
        StandUp.getInstance().getStroll().eventFinished(reward);
    }

    /**
     * Returns the reward accumulated by completing the event.
     *
     * @return the reward.
     */
    public abstract int getReward();

    @Override
    public final boolean keyDown(final int keycode) {
        if (keycode == Input.Keys.BACK || keycode == Input.Keys.F1) {
            final InputAdapter myself = this;
            if(cProcessor instanceof InputMultiplexer) {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        ((InputMultiplexer)cProcessor).removeProcessor(myself);
                    }
                });
            }
            dispose(false);
        }
        return false;
    }

}

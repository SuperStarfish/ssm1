package cg.group4.game_logic.stroll.events;

import cg.group4.game_logic.StandUp;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import cg.group4.view.screen_mechanics.ScreenLogic;
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
     *
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
     * Constructor, creates a new stroll event.
     */
    public StrollEvent() {

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
     * Returns the screen to be displayed.
     *
     * @return the screen
     */
    public abstract ScreenLogic createScreen();

    /**
     * Cleanup after the event.
     */
    protected abstract void clearEvent();

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

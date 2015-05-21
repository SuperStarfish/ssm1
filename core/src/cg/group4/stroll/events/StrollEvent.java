package cg.group4.stroll.events;

import cg.group4.game_logic.GameMechanic;
import cg.group4.game_logic.StandUp;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Disposable;

/**
 * Interface that gets implemented by every event.
 * @author Nick Cleintuar
 * @author Benjamin Los
 * @author Martijn Gribnau
 */
public abstract class StrollEvent extends GameMechanic implements Disposable {

    protected ScreenStore cScreenStore;
	
	/**
	 * Timer to constrain the amount of time spent on an event.
	 */
    protected final TimerTask cTimerTask = new TimerTask() {
        @Override
        public void onTick(final int seconds) {
        }

        @Override
        public void onStart(final int seconds) {
        }

        @Override
        public void onStop() {
            clearEvent();
            dispose();
        }
    };
    
    /**
     * Constructor, creates a new stroll event.
     */
    public StrollEvent() {
        super();
        Gdx.app.log(this.getClass().getSimpleName(), "Event started!");
        StandUp.getInstance().getTimeKeeper().getTimer("EVENT").subscribe(cTimerTask);
        cTimerTask.getTimer().reset();
    }

    public void init() {
        cScreenStore = StandUp.getInstance().getScreenStore();
        cScreenStore.addScreen("Event",createScreen());
        cScreenStore.setScreen("Event");
    }

	/**
	 * Returns the reward accumulated by completing the event.
	 * @return the reward.
	 */
	public abstract int getReward();

    /**
     * Returns the screen to be displayed.
     * @return the screen
     */
    protected abstract ScreenLogic createScreen();

    /**
     * Cleanup after the event.
     */
    protected abstract void clearEvent();
	
	/**
	 * Method that gets called to dispose of the event.
	 */
	public final void dispose() {
        super.dispose();
        Gdx.app.log(this.getClass().getSimpleName(), "Event completed!");
        Timer timer = cTimerTask.getTimer();
        cTimerTask.dispose();
        timer.stop();
        StandUp.getInstance().getStroll().eventFinished(getReward());
	}
}

package cg.group4.stroll.events;

import cg.group4.game_logic.GameMechanic;
import cg.group4.game_logic.StandUp;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Interface that gets implemented by every event.
 * @author Nick Cleintuar
 * @author Benjamin Los
 * @author Martijn Gribnau
 */
public abstract class StrollEvent extends GameMechanic{
    protected final TimerTask cTimerTask = new TimerTask() {
        @Override
        public void onTick(int seconds) {
        }

        @Override
        public void onStart(int seconds) {
        }

        @Override
        public void onStop() {
            dispose();
        }
    };

    public StrollEvent(){
        super();
        Gdx.app.log(this.getClass().getSimpleName(), "Event started!");
        StandUp.getInstance().getTimeKeeper().getTimer("EVENT").subscribe(cTimerTask);
        cTimerTask.getTimer().reset();
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
    public abstract Screen getScreen();
	
	/**
	 * Method that gets called to dispose of the event.
	 */
	public void dispose() {
        super.dispose();
        Gdx.app.log(this.getClass().getSimpleName(), "Event completed!");
        Timer timer = cTimerTask.getTimer();
        cTimerTask.dispose();
        timer.stop();
        getScreen().dispose();
        StandUp.getInstance().getStroll().eventFinished(getReward());
	}
}

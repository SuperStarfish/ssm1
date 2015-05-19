package cg.group4.stroll.events;

import cg.group4.StandUp;
import cg.group4.util.timer.TimerTask;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Interface that gets implemented by every event.
 * @author Nick Cleintuar
 * @author Benjamin Los
 * @author Martijn Gribnau
 */
public abstract class StrollEvent {

    public StrollEvent(){
        Gdx.app.log(this.getClass().getSimpleName(), "Event started!");
//        StandUp.getInstance().getTimeKeeper().getTimer("EVENT").reset(); BROKEN
        StandUp.getInstance().getTimeKeeper().getTimer("EVENT").subscribe(new TimerTask() {
            @Override
            public void onTick(int seconds) {
                update();
            }

            @Override
            public void onStart() {
            }

            @Override
            public void onStop() {
                done();

            }
        });
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
	 * Method that gets called every update cycle. 
	 */
	public abstract void update();
	
	/**
	 * Method that gets called when the stroll event is completed.
	 */
	public void done() {
        Gdx.app.log(this.getClass().getSimpleName(), "Event completed!");

		StandUp.getInstance().getStroll().eventFinished(getReward());
	}
}

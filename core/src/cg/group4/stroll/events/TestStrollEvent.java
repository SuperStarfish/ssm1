package cg.group4.stroll.events;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

import cg.group4.stroll.Stroll;
import cg.group4.view.StrollScreen;

/**
 * Stroll event used for testing.
 * @author Nick Cleintuar
 */
public class TestStrollEvent implements StrollEvent {
	
	/**
	 * Tag used for debugging.
	 */
	private static final String TAG = "TESTSTROLLEVENT";
	
	/**
	 * Saves the current stroll.
	 */
	protected Stroll cCurrentStroll;
	
	/**
	 * Basic variable that makes the event always complete.
	 */
	protected boolean cComplete = true;
	
	/**
	 * Constructor for a test event. Does nothing.
	 * @param stroll	Pointer to the current stroll we are on.
	 */
	public TestStrollEvent(final Stroll stroll) {
		cCurrentStroll = stroll;
	}
	
	@Override
	public final void onUpdate() {
		if (cComplete) {
			onComplete();
		}
	}

	@Override
	public final void onComplete() {
		Gdx.app.log(TAG, "Event completed");
		cCurrentStroll.increaseEventsCompleted();
		((Game) Gdx.app.getApplicationListener()).setScreen(new StrollScreen());
	}

}

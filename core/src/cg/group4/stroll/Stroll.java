package cg.group4.stroll;

import cg.group4.view.RewardScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;


public class Stroll {
	
	/**
	 * Tag used for debugging.
	 */
	private static final String TAG = "[STROLL]";
	
	/**
	 * Chance that an event will occur.
	 */
	protected final float cEventChance = 0.2f;
	
	/**
	 * Amount of events completed, placeholder for the rewards.
	 */
	protected int cEventsCompleted;
	
	/**
	 * Stage containing all the actors. Given with the constructor
	 */
	protected Stage cStrollStage;
	
	/**
	 * Constructor, creates a new Stroll object.
	 * @param strollStage 	Stage needed to update the actors when the Stroll object has been modified.
	 */
	public Stroll(final Stage strollStage) {
		Gdx.app.log(TAG, "Creating new stroll");
		cEventsCompleted = 0;
		cStrollStage = strollStage;
		//this.onComplete();
		timerUpdate();
		//INSERT TIMER THAT COUNTS DOWN
	}
	
	public void timerUpdate() {
//		WindowStyle w = new WindowStyle();
//		w.titleFont = new BitmapFont();
//		Dialog d = new Dialog("An event occured.", w);
//		d.button("Accept");
//		cStrollStage.addActor(d);
	}

	/**
	 * Method that increments the amount of events completed.
	 */
	public final void increaseEventsCompleted() {
		Gdx.app.log(TAG, "Old amount of events completed = " + cEventsCompleted);
		cEventsCompleted++;
		Gdx.app.log(TAG, "New amount of events completed = " + cEventsCompleted);
	}
	
	/**
	 * Method that gets called when the stroll has ended/completed.
	 */
	public final void onComplete() {
		Gdx.app.log(TAG, "Stroll has been completed.");
		((Game) Gdx.app.getApplicationListener()).setScreen(new RewardScreen(cEventsCompleted));
	}
}

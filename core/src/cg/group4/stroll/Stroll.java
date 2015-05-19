package cg.group4.stroll;

import cg.group4.StandUp;
import cg.group4.stroll.events.StrollEvent;
import cg.group4.stroll.events.TestStrollEvent;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import cg.group4.view.RewardScreen;
import cg.group4.view.StrollScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;

/**
 * @author Martijn Gribnau
 * @author Benjamin Los
 */
public class Stroll {
	
	/**
	 * Tag used for debugging.
	 */
	private static final String TAG = Stroll.class.getSimpleName();
	
	/**
	 * Chance that an event will occur.
	 */
	protected final float cEventChance = 0.2f;
	
	/**
	 * Amount of rewards collected.
	 */
	protected int cRewards;
	
	/**
	 * Stage containing all the actors. Given with the constructor
	 */
	protected Stage cStrollStage;
	
	/**
	 * Whether or not you're busy with an event.
	 */
	protected boolean cEventGoing;

	protected Screen cScreen;
	protected Timer cStrollTimer;
    protected Boolean cFinished;
    protected StrollEvent cEvent;
	
	/**
	 * Constructor, creates a new Stroll object.
	 */
	public Stroll() {
		Gdx.app.log(TAG, "Initializing new stroll");
		cRewards = 0;
		cEventGoing = false;

		cScreen = new StrollScreen();
		((Game) Gdx.app.getApplicationListener()).setScreen(cScreen);

        cStrollTimer = StandUp.getInstance().getTimeKeeper().getTimer("STROLL");
        cStrollTimer.reset();
        cStrollTimer.subscribe(new TimerTask() {
            @Override
            public void onTick(int seconds) {
                if (!cEventGoing) {
                    generatePossibleEvent();
                }
            }

            @Override
            public void onStart() {
                cFinished = false;
            }

            @Override
            public void onStop() {
                cFinished = true;
                if (!cEventGoing) {
                    done();
                }
            }
        });
	}

    /**
     * Generate an event on a certain requirement (e.g. a random r: float < 0.1).
     */
    private void generatePossibleEvent() {
        Random rnd = new Random();
        if(rnd.nextFloat() < .1) {
            cEventGoing = true;
            cEvent = new TestStrollEvent();
            cScreen.pause();
            ((Game) Gdx.app.getApplicationListener()).setScreen(cEvent.getScreen());
        }
	}

    /**
     * Handles completion of an event.
     * @param rewards Reward(s) given on completion of an event
     */
    public void eventFinished(int rewards) {
//        Gdx.app.log(cEvent.getClass().getSimpleName(), "Event completed!");

        cRewards += rewards;
        cEventGoing = false;
        cEvent = null;

        if(cFinished) {
            done();
		} else {
			((Game) Gdx.app.getApplicationListener()).setScreen(cScreen);
		}
    }


	
	/**
	 * Method that gets called when the stroll has ended/completed.
	 */
	public final void done() {
		Gdx.app.log(TAG, "Stroll has ended.");
		cScreen.dispose();
		((Game) Gdx.app.getApplicationListener()).setScreen(new RewardScreen(cRewards));
	}
}

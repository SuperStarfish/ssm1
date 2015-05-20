package cg.group4.stroll;

import cg.group4.game_logic.GameMechanic;
import cg.group4.game_logic.StandUp;
import cg.group4.stroll.events.StrollEvent;
import cg.group4.stroll.events.TestStrollEvent;
import cg.group4.util.camera.WorldRenderer;
import cg.group4.util.timer.TimerTask;
import cg.group4.view.RewardScreen;
import cg.group4.view.ScreenLogic;
import cg.group4.view.StrollScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import java.util.Random;

/**
 * @author Martijn Gribnau
 * @author Benjamin Los
 */
public class Stroll extends GameMechanic {
	
	/**
	 * Tag used for debugging.
	 */
	private static final String TAG = Stroll.class.getSimpleName();
	
	/**
	 * Amount of rewards collected.
	 */
	protected int cRewards;

    /**
     * The chance of an event happening this second.
     */
    protected double cEventThreshold;
	
	/**
	 * Whether or not you're busy with an event.
	 */
	protected boolean cEventGoing;

    /**
     * Whether the stroll has ended or not.
     */
    protected Boolean cFinished;

    /**
     * Pointer to the worldRenderer.
     */
    protected WorldRenderer cWorldRenderer;

    /**
     * The screen belonging to this stroll.
     */
	protected ScreenLogic cScreen;
	
	/**
	 * The base threshold used for generating events.
	 */
	protected static final double BASE_THRESHOLD = 0.002;

    /**
     * The timertask to listen to the stroll timer.
     */
	protected final TimerTask cTimerTask = new TimerTask() {
        @Override
        public void onTick(final int seconds) {
        }

        @Override
        public void onStart(final int seconds) {
            cFinished = false;
        }

        @Override
        public void onStop() {
            cFinished = true;
            if (!cEventGoing) {
                done();
            }
        }
    };

    /**
     * The current event being played.
     */
    protected StrollEvent cEvent;

	/**
	 * Constructor, creates a new Stroll object.
	 */
	public Stroll() {
		Gdx.app.log(TAG, "Started new stroll");
		cRewards = 0;
		cEventGoing = false;
        cFinished = false;
        cEventThreshold = BASE_THRESHOLD;

        cWorldRenderer = StandUp.getInstance().getWorldRenderer();
        cScreen = new StrollScreen(cWorldRenderer);
        cWorldRenderer.setScreen(cScreen);
//		cScreen = new StrollScreen(StandUp.getInstance());
//        ((Game) Gdx.app.getApplicationListener()).setScreen(cScreen);

        StandUp.getInstance().getTimeKeeper().getTimer("STROLL").subscribe(cTimerTask);
        cTimerTask.getTimer().reset();
	}
	
	/**
	 * Every cycle, as long as there is no event going on, we want to generate an event.
	 */
    @Override
    public final void update() {
        if (!cEventGoing) {
            generatePossibleEvent();
        }
    }
    
    /**
     * Resumes the scroll if it is somehow paused.
     */
    public final void resume() {
        Gdx.app.log(TAG, "Resumed stroll");
        cWorldRenderer.setScreen(cScreen);
    }

    /**
     * Generate an event on a certain requirement (e.g. a random r: float < 0.1).
     */
    protected final void generatePossibleEvent() {
        Random rnd = new Random();
        if (rnd.nextFloat() < cEventThreshold) {
            cEventGoing = true;
            cEvent = new TestStrollEvent();
            cWorldRenderer.setScreen(cEvent.getScreen());
//            ((Game) Gdx.app.getApplicationListener()).setScreen(cEvent.getScreen());
        }
	}

    /**
     * Handles completion of an event.
     * @param rewards Reward(s) given on completion of an event
     */
    public final void eventFinished(final int rewards) {
    	Gdx.app.log(TAG, "Event completed!");

        cRewards += rewards;
        cEvent = null;
        cEventGoing = false;

        if (cFinished) {
        	Gdx.app.log(TAG, "Event finished and time is up, ending stroll.");
            done();
		} else {
			Gdx.app.log(TAG, "Event finished and there is time left, returning back to strollscreen");
            //cWorldRenderer.setScreen(cScreen);
			cScreen.setAsActiveScreen();
		}
    }


	
	/**
	 * Method that gets called when the stroll has ended/completed.
	 */
	public final void done() {
        StandUp.getInstance().unSubscribe(this);
		Gdx.app.log(TAG, "Stroll has ended.");
		cScreen.dispose();
        cTimerTask.dispose();
        ((Game) Gdx.app.getApplicationListener()).setScreen(new RewardScreen(cRewards));
        StandUp.getInstance().endStroll(cRewards);
	}
}

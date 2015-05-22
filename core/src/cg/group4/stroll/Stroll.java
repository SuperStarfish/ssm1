package cg.group4.stroll;

import cg.group4.game_logic.GameMechanic;
import cg.group4.game_logic.StandUp;
import cg.group4.stroll.events.StrollEvent;
import cg.group4.stroll.events.TestStrollEvent;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.TimerTask;
import cg.group4.view.screen.RewardScreen;
import cg.group4.view.screen.StrollScreen;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.Gdx;

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
     * Pointer to the screenStore.
     */
    protected ScreenStore cScreenStore;
	
	/**
	 * The base threshold used for generating events.
	 */
	protected static final double BASE_THRESHOLD = 0.002;

    /**
     * The timer task to listen to the stroll timer.
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

        cScreenStore = ScreenStore.getInstance();

        cScreenStore.addScreen("Stroll", new StrollScreen());
        cScreenStore.setScreen("Stroll");

        TimeKeeper.getInstance().getTimer("STROLL").subscribe(cTimerTask);
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
        cScreenStore.setScreen("Stroll");
    }

    /**
     * Generate an event on a certain requirement (e.g. a random r: float < 0.1).
     */
    protected final void generatePossibleEvent() {
        Random rnd = new Random();
        if (rnd.nextFloat() < cEventThreshold) {
            cEventGoing = true;
            cEvent = new TestStrollEvent();
            cEvent.init();
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
			cScreenStore.setScreen("Stroll");
		}
    }


	
	/**
	 * Method that gets called when the stroll has ended/completed.
	 */
	public final void done() {
        StandUp.getInstance().unSubscribe(this);
		Gdx.app.log(TAG, "Stroll has ended.");
        cTimerTask.dispose();

        cScreenStore.addScreen("Reward", new RewardScreen());
        cScreenStore.setScreen("Reward");

        StandUp.getInstance().endStroll(cRewards);
	}
}

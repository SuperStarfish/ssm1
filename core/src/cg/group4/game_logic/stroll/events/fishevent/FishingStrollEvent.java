package cg.group4.game_logic.stroll.events.fishevent;

import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.util.sensors.Accelerometer;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

import java.util.Observable;
import java.util.Observer;

/**
 * Stroll event where you have to fish to complete it.
 */
public class FishingStrollEvent extends StrollEvent {

    /**
     * Amount of points that you should gain when completing this event.
     */
	protected static final int REWARDS = 10;

	/**
	 * Sound played when the task is completed.
	 */
	protected Sound cCompletedTaskSound;

	/**
	 * The state where the event is in.
	 */
	protected FishEventState cState;

	/**
	 * Observer for the input delay timer, prevents people from completing the task instantaneous.
	 * Changes the input delay boolean.
	 */
	protected Observer cDelayInputStartObserver;

	/**
	 * Timer for the input delay, prevents people from completing the task instantaneous.
	 */
	protected Timer cDelayInputTimer;

	/**
	 * Observer for the input delay timer, prevents people from completing the task instantaneous.
	 * Changes the input delay boolean.
	*/
	protected Observer cDelayInputStopObserver;

	/**
	 * Boolean for the input delay, prevents people from completing the task instantaneous.
	 */
	protected boolean cDelayNewInput;

	/**
	 * Accelerometer used to fetch the input to complete the event.
	 */
	protected Accelerometer cAccelMeter;

	/**
	 * The observer for the timer, on stop it should switch the state of the event.
	 */
	protected Observer cFishStopObserver;

	/**
	 * The timer which keeps track for how long you hold still.
	 */
	protected Timer cFishTimer;

	/**
	 * Creates a new fishing event, with delay timer, text, screen and input.
	 */
	public FishingStrollEvent() {
		super();

		cCompletedTaskSound = Gdx.audio.newSound(Gdx.files.internal("sounds/completedTask.wav"));

        cDelayInputStartObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                cDelayNewInput = true;
            }
        };

        cDelayInputStopObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                cDelayNewInput = false;
            }
        };

		cDelayInputTimer = new Timer("DELAYFISHEVENT", 1);
		TimerStore.getInstance().addTimer(cDelayInputTimer);
		cDelayInputTimer.getStartSubject().addObserver(cDelayInputStartObserver);
        cDelayInputTimer.getStopSubject().addObserver(cDelayInputStopObserver);
        
        cAccelMeter = new Accelerometer(StandUp.getInstance().getSensorReader());
        cAccelMeter.filterGravity(true);

		cDelayInputTimer.reset();
        cState = new CastForwardState(this);
	}
	
	@Override
	public final void update(final Observable o, final Object arg) {
		Vector3 accel = cAccelMeter.update();
        if (!cDelayNewInput) {
            processInput(accel);
        }
	}

	/**
	 * Gets called when the event is completed.
	 */
    public final void eventCompleted() {
        clearEvent();
    }

    /**
     * Processes the input for the event, takes the processInput method of his state.
     * @param input Vector containg the acceleration in the x,y,z direction.
     */
	protected void processInput(final Vector3 input) {
		cState.processInput(input);
	}

	@Override
	public final int getReward() {
		return REWARDS;
	}

	@Override
	protected final void clearEvent() {
		super.dispose();
		TimerStore.getInstance().removeTimer(cDelayInputTimer);
		TimerStore.getInstance().removeTimer(cFishTimer);
	}

    public void setText(String text) {
        cLabelSubject.update(text);
    }
}

package cg.group4.game_logic.stroll.events.fishevent;

import java.util.Observable;
import java.util.Observer;

import cg.group4.game_logic.stroll.events.StrollEvent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import cg.group4.game_logic.StandUp;
import cg.group4.util.sensors.Accelerometer;
import cg.group4.util.timer.Timer;
import cg.group4.view.screen.EventScreen;
import cg.group4.view.screen_mechanics.ScreenLogic;

public class FishingStrollEvent extends StrollEvent {

    /**
     * Amount of points that you should gain when completing this event.
     */
	protected static final int REWARDS = 10;

    /**
     * The screen this event belongs to.
     */
	protected EventScreen cScreen;

	protected Sound cCompletedTaskSound;

	protected Label cLabel;

	protected FishEventState cState;

	protected Observer cDelayInputStartObserver;

	protected Timer cDelayInputTimer;

	protected Observer cDelayInputStopObserver;

	protected boolean cDelayNewInput;

	protected Accelerometer cAccelMeter;
	
	public FishingStrollEvent() {
		super();
        cScreen = new EventScreen();
        cLabel = cScreen.getLabel();

        cDelayInputStartObserver = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                cDelayNewInput = true;
            }
        };

        cDelayInputStopObserver = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                cDelayNewInput = false;
            }
        };

        cDelayInputTimer = new Timer("DELAYFISHEVENT", 1);
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
	private void processInput(final Vector3 input) {
		cState.processInput(input);
	}

	@Override
	public final int getReward() {
		return REWARDS;
	}

	@Override
	public final ScreenLogic createScreen() {
		return cScreen;
	}

	@Override
	protected final void clearEvent() {
		super.dispose();
	}
}

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
	
	protected final static int REWARDS = 10;
	
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
        cState = new CastBackState(this);
	}
	
	@Override
	public final void update(Observable o, Object arg) {
		Vector3 accel = cAccelMeter.update();
        if (!cDelayNewInput) {
            processInput(accel);
        }
	}

    public final void eventCompleted() {
        clearEvent();
    }

	private void processInput(Vector3 input) {
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
	protected void clearEvent() {
		super.dispose();
        cDelayInputTimer.getStartSubject().deleteObserver(cDelayInputStartObserver);
        cDelayInputTimer.getStopSubject().deleteObserver(cDelayInputStopObserver);
        cDelayInputTimer.dispose();
		
	}
}

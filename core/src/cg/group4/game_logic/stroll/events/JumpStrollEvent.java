package cg.group4.game_logic.stroll.events;

import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import cg.group4.game_logic.StandUp;
import cg.group4.util.sensors.Accelerometer;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.Timer;
import cg.group4.view.screen.EventScreen;
import cg.group4.view.screen_mechanics.ScreenLogic;

public class JumpStrollEvent extends StrollEvent {
	
	protected final static int REWARDS = 10;
	protected final static int TIME_FRAME = 20;
	
	
	protected EventScreen cScreen;

	protected Sound cCompletedTaskSound;

	protected Label cLabel;

	protected Observer cDelayInputStartObserver;

	protected Timer cDelayInputTimer;

	protected Observer cDelayInputStopObserver;

	protected boolean cDelayNewInput;
	protected Accelerometer cAccelMeter;
	
	public JumpStrollEvent(){
		super();
        cScreen = new EventScreen();
        cLabel = cScreen.getLabel();
        cCompletedTaskSound = Gdx.audio.newSound(Gdx.files.internal("sounds/completedTask.wav"));

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

        cDelayInputTimer = new Timer("DELAYJUMPEVENT", 1);
        cDelayInputTimer.getStartSubject().addObserver(cDelayInputStartObserver);
        cDelayInputTimer.getStopSubject().addObserver(cDelayInputStopObserver);
        
        cAccelMeter = new Accelerometer(StandUp.getInstance().getSensorReader());
        cAccelMeter.filterGravity(true);
        
        cDelayNewInput = false;
        generateTask();
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Vector3 accel = cAccelMeter.update();
        if (!cDelayNewInput) {
            processInput(accel);
        }
	}

	private void processInput(Vector3 input) {
		TimeKeeper.getInstance().getTimer("DELAYJUMPEVENT").getRemainingTime();
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
		// TODO Auto-generated method stub
		
	}
	
	public final void generateTask() {
		
	}
}

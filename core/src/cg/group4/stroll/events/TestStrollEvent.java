package cg.group4.stroll.events;

import cg.group4.game_logic.StandUp;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import cg.group4.view.EventScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 * Stroll event used for testing.
 * @author Nick Cleintuar
 */
public class TestStrollEvent extends StrollEvent {
	
	/**
	 * The screen where the event is displayed.
	 */
	protected EventScreen cScreen;
	
	/**
	 * The label where the event instructions are displayed.
	 */
	protected Label cLabel;
	
	/**
	 * The accelerometer values of the previous update.
	 * Used to cancel noise and reactionary acceleration.
	 */
	protected Vector3 base;
	
	/**
	 * operationNr: Movement operation that must be done.
	 * prevOperationNr: Previous movement operation that was done.
	 * tasksCompleted: Number of completed movement operations.
	 */
	protected int operationNr, prevOperationNr, tasksCompleted;
	
	/**
	 * Number of tasks that the player must complete before the event is considered a success.
	 */
	protected final int maxTasks = 10;

	/**
	 * Number of tasks that the player must complete before the event is considered a success.
	 */
	protected static final int REWARDS = 10;
	
	/**
	 * Sound effect played when a task is completed.
	 */
	protected final Sound cCompletedTaskSound;

	/**
	 * Whether or not the input is delayed.
	 */
	protected boolean cDelayNewInput;

	/**
	 * Delays input to better determine the acceleration direction.
	 */
	protected Timer cDelayInputTimer;

	/**
	 * Direction of the current task.
	 */
	protected String direction;

	/**
	 * Constants used for each task case.
	 */
	protected static final int MOVE_LEFT = 1, MOVE_RIGHT = 2, MOVE_DOWN = 3, MOVE_UP = 4,
			MOVE_AWAY = 5, MOVE_TOWARDS = 6;
	
	/**
	 * Tasks which will execute when a delay is initiated and stopped.
	 */
	TimerTask delayInputTasks;
	
	/**
	 * Constructor for the test event.
	 */
	public TestStrollEvent() {
		super();
		cScreen = new EventScreen();
		cLabel = cScreen.getLabel();
		cCompletedTaskSound = Gdx.audio.newSound(Gdx.files.internal("sounds/completedTask.wav"));
		tasksCompleted = 0;
		prevOperationNr = 0;
		
		delayInputTasks = new TimerTask() {
			@Override
		    public void onTick(int seconds) {
			}

		    @Override
		    public void onStart(int seconds) {
		    	cLabel.setText("Wrong! Try " + direction + " again!");
		    	cDelayNewInput = true;
		    }

		    @Override
		    public void onStop() {
		    	cDelayNewInput = false;
		    }
		};
		
		cDelayInputTimer = new Timer("DELAYEVENTINPUT", 1);
		cDelayInputTimer.subscribe(delayInputTasks);
		cDelayNewInput = false;
		
		
		base = new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
		//((Game) Gdx.app.getApplicationListener()).setScreen(cScreen);
		doTask();
	}
	
	public final void doTask() {
		//System.out.println("DOTASK!");
		operationNr = (int) Math.floor(Math.random() * 6 + 1);
		if (operationNr == prevOperationNr) {
			operationNr++;
		}
		switch(operationNr) {
			case MOVE_LEFT:
				direction = "left";
				cLabel.setText("Move your phone to the left!");
				break;
			case MOVE_RIGHT:
				direction = "right";
				cLabel.setText("Move your phone to the right!");
				break;
			case MOVE_DOWN:
				direction = "down";
				cLabel.setText("Move your phone down!");
				break;
			case MOVE_UP:
				direction = "up";
				cLabel.setText("Move your phone up!");
				break;
			case MOVE_AWAY:
				direction = "away from you";
				cLabel.setText("Move your phone away from you!");
				break;
			case MOVE_TOWARDS:
				direction = "towards you";
				cLabel.setText("Move your phone towards you!");
				break;
			default:
				cLabel.setText("doTask Unknown Operation Number: " + operationNr);
				break;
		}
	}
	
	/**
	 * Gets called when one of the individual tasks gets completed.
	 */
	public final void taskCompleted() {
		this.tasksCompleted++;
		cCompletedTaskSound.play(1.0f);
		if (this.tasksCompleted < this.maxTasks) {
			//cDelayTaskTimer.reset();
			prevOperationNr = operationNr;
			//cLabel.setText("Nice!");
			doTask();
		} else {
			//cLabel.setText("You completed the event! Good job!");
			clearEvent();
		}
	}
	
	public final void clearEvent() {
		delayInputTasks.dispose();
		cDelayInputTimer.stop();
		Gdx.app.getPreferences("TIMER").clear();
		Gdx.app.getPreferences("TIMER").flush();
		dispose();
	}
	
	/**
	 * Checks if one of the individual tasks gets completed.
	 * @param accelData 	Vector containing the acceleration in the x,y and z direction.
	 */
	public final void processInput(final Vector3 accelData) {
//		//System.out.println("processedInput: X: " + accelData.x + " Y: " + accelData.y + " Z: " + accelData.z);
		final float delta = 2.0f;
		float highestAccel = determineHighestAccelerationComponent(accelData);
		if(highestAccel >= delta) {
			switch(operationNr) {
				case MOVE_LEFT:
					cDelayInputTimer.reset();
					if (accelData.x <= -delta) {
						System.out.println("Left Succes!");
						//base.x = -1 * accelData.x;
						taskCompleted();
					}
					break;
				case MOVE_RIGHT:
					cDelayInputTimer.reset();
					if (accelData.x >= delta) {
						System.out.println("Right Succes!");
						//base.x = -1 * accelData.x;
						taskCompleted();
					} 
					break;
				case MOVE_DOWN:
					cDelayInputTimer.reset();
					if (accelData.y <= -delta) {
						System.out.println("Down Succes!");
						//base.y = -1 * accelData.y;
						taskCompleted();
					}
					break;
				case MOVE_UP:
					cDelayInputTimer.reset();
					if (accelData.y >= delta) {
						System.out.println("Up Succes!");
						//base.y = -1 * accelData.y;
						taskCompleted();
					}
					break;
				case MOVE_AWAY:
					cDelayInputTimer.reset();
					if (accelData.z <= -delta) {
						System.out.println("Away Succes!");
						//base.z = -1 * accelData.z;
						taskCompleted();
					}
					break;
				case MOVE_TOWARDS:
					cDelayInputTimer.reset();
					if (accelData.z >= delta) {
						System.out.println("Toward Succes!");
						//base.z = -1 * accelData.z;
						taskCompleted();
					}
					break;
				default:
					cLabel.setText("processInput Unknown Operation Number: " + operationNr);
					break;
			}
		}
	}
	
	public float determineHighestAccelerationComponent(Vector3 accelData) {
		float result = Math.abs(accelData.x);
		if (result < Math.abs(accelData.y)) {
			result = Math.abs(accelData.y);
		}
		if (result < Math.abs(accelData.z)) {
			result = Math.abs(accelData.z);
		}
		return result;
	}
	
    @Override
    public final int getReward() {
        return REWARDS;
    }

    @Override
    public final Screen getScreen() {
        return cScreen;
    }

    @Override
    public final void update() {
        final float accelX = Gdx.input.getAccelerometerX();
        final float accelY = Gdx.input.getAccelerometerY();
        final float accelZ = Gdx.input.getAccelerometerZ();
        
        Vector3 current = new Vector3();
        
        final float noiseLevel = 1.5f;
        if (Math.abs(base.x - accelX) > noiseLevel) {
        	current.x = accelX - base.x;
        }
        if (Math.abs(base.y - accelY) > noiseLevel) {
        	current.y = accelY - base.y;
        }
        if (Math.abs(base.z - accelZ) > noiseLevel) {
        	current.z = accelZ - base.z;
        }
      
        base.x = accelX;
        base.y = accelY;
        base.z = accelZ;
        if (!cDelayNewInput) {
        	processInput(current);
        }
    }
}

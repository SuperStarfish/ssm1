package cg.group4.stroll.events;

import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import cg.group4.view.screen.EventScreen;
import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Observable;

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
	protected static final int MAX_TASKS = 10;
	
	/**
	 * Amount of different tasks.
	 */
	protected static final int AMOUNT_OF_TASKS = 6;

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
	protected TimerTask delayInputTasks;
	
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
		    public void onTick(final int seconds) {
			}

		    @Override
		    public void onStart(final int seconds) {
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
		doTask();
	}
	
	/**
	 * Sets the new operation that should be done.
	 */
	public final void doTask() {
		operationNr = (int) Math.floor(Math.random() * AMOUNT_OF_TASKS + 1);
		if (operationNr == prevOperationNr) {
			if (operationNr == AMOUNT_OF_TASKS) {
				operationNr = 1;
			} else {
				operationNr++;
			}
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
		if (this.tasksCompleted < MAX_TASKS) {
			//cDelayTaskTimer.reset();
			prevOperationNr = operationNr;
			//cLabel.setText("Nice!");
			doTask();
		} else {
			//cLabel.setText("You completed the event! Good job!");
			clearEvent();
		}
	}
	
	/**
	 * Clears the current event.
	 */
	public final void clearEvent() {
        super.dispose();
		delayInputTasks.dispose();
		cDelayInputTimer.dispose();
	}
	
	/**
	 * Checks if one of the individual tasks gets completed.
	 * @param accelData 	Vector containing the acceleration in the x,y and z direction.
	 */
	public final void processInput(final Vector3 accelData) {
//		//System.out.println("processedInput: X: " + accelData.x + " Y: " + accelData.y + " Z: " + accelData.z);
		final float delta = 2.0f;
		float highestAccel = determineHighestAccelerationComponent(accelData);
		if (highestAccel >= delta) {
			switch(operationNr) {
				case MOVE_LEFT:
					cDelayInputTimer.reset();
					//if (accelData.x <= -delta) {
					if (accelData.y >= delta) {
						System.out.println("Left Succes!");
						//base.x = -1 * accelData.x;
						taskCompleted();
					}
					break;
				case MOVE_RIGHT:
					cDelayInputTimer.reset();
					//if (accelData.x >= delta) {
					if (accelData.y <= delta) {
						System.out.println("Right Succes!");
						//base.x = -1 * accelData.x;
						taskCompleted();
					} 
					break;
				case MOVE_DOWN:
					cDelayInputTimer.reset();
					//if (accelData.y <= -delta) {
					if (accelData.x <= -delta) {
						System.out.println("Down Succes!");
						//base.y = -1 * accelData.y;
						taskCompleted();
					}
					break;
				case MOVE_UP:
					cDelayInputTimer.reset();
					//if (accelData.y >= delta) {
					if (accelData.x >= delta) {
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
	
	/**
	 * Determines the highest acceleration component to disable the ability to cheat this event.
	 * @param accelData Vector containing the acceleration in the x, y and z direction.
	 * @return Returns the highest acceleration.
	 */
	public final float determineHighestAccelerationComponent(final Vector3 accelData) {
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
    public final ScreenLogic createScreen() {
        return cScreen;
    }

    @Override
	public void update(Observable o, Object arg)  {
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

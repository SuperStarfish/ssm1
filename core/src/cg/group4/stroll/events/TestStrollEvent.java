package cg.group4.stroll.events;

import cg.group4.view.EventScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector3;
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
	 * Constructor for the test event.
	 */
	public TestStrollEvent() {
		super();
		cScreen = new EventScreen();
		cLabel = cScreen.getLabel();
		tasksCompleted = 0;
		prevOperationNr = 0;
		base = new Vector3(Gdx.input.getAccelerometerX(), Gdx.input.getAccelerometerY(), Gdx.input.getAccelerometerZ());
		((Game) Gdx.app.getApplicationListener()).setScreen(cScreen);
		doTask();
	}
	
	public final void doTask() {
		operationNr = (int) Math.floor(Math.random() * 6 + 1);
		if (operationNr == prevOperationNr) {
			operationNr++;
		}
		switch(operationNr) {
			case 1:
				cLabel.setText("Move your phone to the left!");
				break;
			case 2:
				cLabel.setText("Move your phone to the right!");
				break;
			case 3:
				cLabel.setText("Move your phone down!");
				break;
			case 4:
				cLabel.setText("Move your phone up!");
				break;
			case 5:
				cLabel.setText("Move your phone away from you!");
				break;
			case 6:
				cLabel.setText("Move your phone towards you!");
				break;
			default:
				break;
		}
	}
	
	public final void taskCompleted() {
		//System.out.println("TASK COMPLETEDDDDDDDDDDDDDdd");
		this.tasksCompleted++;
		if (this.tasksCompleted < this.maxTasks) {
			prevOperationNr = operationNr;
			doTask();
		} else {
			cLabel.setText("You completed the event! Good job!");
			dispose();
		}
	}
	
	public final void processInput(final Vector3 accelData) {
		//System.out.println("processedInput: X: " + accelData.x + " Y: " + accelData.y + " Z: " + accelData.z);
		final float delta = 2.5f;
		switch(operationNr) {
			case 1:
				if (accelData.x <= -delta) {
					System.out.println("Left Succes!");
					base.x = -1 * accelData.x;
					taskCompleted();
				}
				break;
			case 2:
				if (accelData.x >= delta) {
					System.out.println("Right Succes!");
					base.x = -1 * accelData.x;
					taskCompleted();
				}
				break;
			case 3:
				if (accelData.y <= -delta) {
					System.out.println("Down Succes!");
					base.y = -1 * accelData.y;
					taskCompleted();
				}
				break;
			case 4:
				if (accelData.y >= delta) {
					System.out.println("Up Succes!");
					base.y = -1 * accelData.y;
					taskCompleted();
				}
				break;
			case 5:
				if (accelData.z <= -delta) {
					System.out.println("Away Succes!");
					base.z = -1 * accelData.z;
					taskCompleted();
				}
				break;
			case 6:
				if (accelData.z >= delta) {
					System.out.println("Toward Succes!");
					base.z = -1 * accelData.z;
					taskCompleted();
				}
				break;
			default:
				break;
		}
	}

    @Override
    public final int getReward() {
        return 10;
    }

    @Override
    public final Screen getScreen() {
        return cScreen;
    }

    @Override
    public final void update(){
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
        processInput(current);
    }
}

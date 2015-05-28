package cg.group4.game_logic.stroll.events;

import cg.group4.game_logic.StandUp;
import cg.group4.util.sensors.Accelerometer;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import cg.group4.view.screen.EventScreen;
import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Observable;
import java.util.Observer;

/**
 * Stroll event used for testing.
 *
 * @author Nick Cleintuar
 */
public class TestStrollEvent extends StrollEvent {

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
     * Constants used for each task case.
     */
    protected static final int MOVE_LEFT = 1, MOVE_RIGHT = 2, MOVE_DOWN = 3, MOVE_UP = 4,
            MOVE_AWAY = 5, MOVE_TOWARDS = 6;
    /**
     * Sound effect played when a task is completed.
     */
    protected final Sound cCompletedTaskSound;
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
    protected Vector3 cBase;
    /**
     * cOperationNr: Movement operation that must be done.
     * cPrevOperationNr: Previous movement operation that was done.
     * cTasksCompleted: Number of completed movement operations.
     */
    protected int cOperationNr, cPrevOperationNr, cTasksCompleted;
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
    protected String cDirection;
    /**
     * Tasks which will execute when a delay is initiated.
     */
    protected Observer cDelayInputStartObserver;
    /**
     * Tasks which will execute when a delay is stopped.
     */
    protected Observer cDelayInputStopObserver;

    /**
     * Configurable accelerometer that reads and filters the accelerations of the device.
     */
    protected Accelerometer cAccelMeter;

    /**
     * Constructor for the test event.
     */
    public TestStrollEvent() {
        super();
        cScreen = new EventScreen();
        cLabel = cScreen.getLabel();
        cCompletedTaskSound = Gdx.audio.newSound(Gdx.files.internal("sounds/completedTask.wav"));
        cTasksCompleted = 0;
        cPrevOperationNr = 0;

        cDelayInputStartObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                cLabel.setText("Wrong! Try " + cDirection + " again!");
                cDelayNewInput = true;
            }
        };

        cDelayInputStopObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                cDelayNewInput = false;
            }
        };

        cDelayInputTimer = new Timer("DELAYEVENTINPUT", 1);
        cDelayInputTimer.getStartSubject().addObserver(cDelayInputStartObserver);
        cDelayInputTimer.getStopSubject().addObserver(cDelayInputStopObserver);
        TimerStore.getInstance().addTimer(cDelayInputTimer);

        cDelayNewInput = false;

        cAccelMeter = new Accelerometer(StandUp.getInstance().getSensorReader());
        cAccelMeter.filterGravity(true);

        cBase = new Vector3(
                Gdx.input.getAccelerometerX(),
                Gdx.input.getAccelerometerY(),
                Gdx.input.getAccelerometerZ());
        doTask();
    }

    /**
     * Sets the new operation that should be done.
     */
    public final void doTask() {
        cOperationNr = (int) Math.floor(Math.random() * AMOUNT_OF_TASKS + 1);
        if (cOperationNr == cPrevOperationNr) {
            if (cOperationNr == AMOUNT_OF_TASKS) {
                cOperationNr = 1;
            } else {
                cOperationNr++;
            }
        }
        switch (cOperationNr) {
            case MOVE_LEFT:
                cDirection = "left";
                cLabel.setText("Move your phone to the left!");
                break;
            case MOVE_RIGHT:
                cDirection = "right";
                cLabel.setText("Move your phone to the right!");
                break;
            case MOVE_DOWN:
                cDirection = "down";
                cLabel.setText("Move your phone down!");
                break;
            case MOVE_UP:
                cDirection = "up";
                cLabel.setText("Move your phone up!");
                break;
            case MOVE_AWAY:
                cDirection = "away from you";
                cLabel.setText("Move your phone away from you!");
                break;
            case MOVE_TOWARDS:
                cDirection = "towards you";
                cLabel.setText("Move your phone towards you!");
                break;
            default:
                cLabel.setText("doTask Unknown Operation Number: " + cOperationNr);
                break;
        }
    }

    /**
     * Gets called when one of the individual tasks gets completed.
     */
    public final void taskCompleted() {
        this.cTasksCompleted++;
        cCompletedTaskSound.play(1.0f);
        if (this.cTasksCompleted < MAX_TASKS) {
            //cDelayTaskTimer.reset();
            cPrevOperationNr = cOperationNr;
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
        TimerStore.getInstance().removeTimer(cDelayInputTimer);
    }

    /**
     * Checks if one of the individual tasks gets completed.
     *
     * @param accelData Vector containing the acceleration in the x,y and z direction.
     */
    public final void processInput(final Vector3 accelData) {
        //System.out.println("processedInput: X: " + accelData.x + " Y: " + accelData.y + " Z: " + accelData.z);

        final float highestAccel = cAccelMeter.highestAccelerationComponent(accelData);
        final float delta = 2.0f;

        if (highestAccel >= delta) {
            switch (cOperationNr) {
                case MOVE_LEFT:
                    cDelayInputTimer.reset();
                    //if (accelData.x <= -delta) {
                    if (accelData.y >= delta) {
                        System.out.println("Left Succes!");
                        //cBase.x = -1 * accelData.x;
                        taskCompleted();
                    }
                    break;
                case MOVE_RIGHT:
                    cDelayInputTimer.reset();
                    //if (accelData.x >= delta) {
                    if (accelData.y <= delta) {
                        System.out.println("Right Succes!");
                        //cBase.x = -1 * accelData.x;
                        taskCompleted();
                    }
                    break;
                case MOVE_DOWN:
                    cDelayInputTimer.reset();
                    //if (accelData.y <= -delta) {
                    if (accelData.x <= -delta) {
                        System.out.println("Down Succes!");
                        //cBase.y = -1 * accelData.y;
                        taskCompleted();
                    }
                    break;
                case MOVE_UP:
                    cDelayInputTimer.reset();
                    //if (accelData.y >= delta) {
                    if (accelData.x >= delta) {
                        System.out.println("Up Succes!");
                        //cBase.y = -1 * accelData.y;
                        taskCompleted();
                    }
                    break;
                case MOVE_AWAY:
                    cDelayInputTimer.reset();
                    if (accelData.z <= -delta) {
                        System.out.println("Away Succes!");
                        //cBase.z = -1 * accelData.z;
                        taskCompleted();
                    }
                    break;
                case MOVE_TOWARDS:
                    cDelayInputTimer.reset();
                    if (accelData.z >= delta) {
                        System.out.println("Toward Succes!");
                        //cBase.z = -1 * accelData.z;
                        taskCompleted();
                    }
                    break;
                default:
                    cLabel.setText("processInput Unknown Operation Number: " + cOperationNr);
                    break;
            }
        }
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
    public final void update(final Observable o, final Object arg) {
        Vector3 readings = cAccelMeter.update();
        //Done outside of the if to keep the resulting readings relevant. Needs testing
        if (!cDelayNewInput) {
            processInput(readings);
        }
    }
}

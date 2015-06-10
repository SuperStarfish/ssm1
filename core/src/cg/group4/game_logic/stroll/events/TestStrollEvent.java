package cg.group4.game_logic.stroll.events;

import cg.group4.game_logic.StandUp;
import cg.group4.util.audio.AudioPlayer;
import cg.group4.util.sensor.Accelerometer;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector3;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Stroll event used for testing.
 */
public class TestStrollEvent extends StrollEvent {

    /**
     * Number of tasks that the player must complete before the event is considered a success.
     */
    protected static final int MAX_TASKS = 10;
   
    /**
     * Constants used for each task case.
     */
    protected static final int MOVE_LEFT = 0, MOVE_RIGHT = 1, MOVE_DOWN = 2, MOVE_UP = 3,
            MOVE_AWAY = 4, MOVE_TOWARDS = 5;
   
    /**
     * Sound effect played when a task is completed.
     */
    protected final Sound cCompletedTaskSound;
    
    /**
     * The string values belonging to each direction.
     */
    protected String[] cDirections = {"to the left", "to the right", "down", "up", "away from you", "towards you"};
    
    /**
     * operationNr: Movement operation that must be done.
     * prevOperationNr: Previous movement operation that was done.
     * tasksCompleted: Number of completed movement operations.
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
     * Creates random variables for the class.
     */
    protected Random cRandom;

    /**
     * Constructor for the test event.
     */
    public TestStrollEvent() {
        super();
        cCompletedTaskSound = Gdx.audio.newSound(Gdx.files.internal("sounds/completedTask.wav"));
        cTasksCompleted = 0;
        cPrevOperationNr = -1;
        cRandom = new Random();

        cDelayInputStartObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                cDelayNewInput = true;
            }
        };

        cDelayInputStopObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                cLabelSubject.update("Move your phone " + cDirections[cOperationNr] + "!");
                cDelayNewInput = false;
            }
        };

        cDelayInputTimer = new Timer("DELAYEVENTINPUT", 1);
        cDelayInputTimer.getStartSubject().addObserver(cDelayInputStartObserver);
        cDelayInputTimer.getStopSubject().addObserver(cDelayInputStopObserver);

        cDelayNewInput = false;

        cAccelMeter = new Accelerometer(StandUp.getInstance().getSensorReader());
    }

    /**
     * Sets the new operation that should be done.
     */
    public void doTask() {
        do {
            cOperationNr = cRandom.nextInt(cDirections.length);
        } while (cOperationNr == cPrevOperationNr);

    }

    /**
     * Gets called when one of the individual tasks gets completed.
     */
    public void taskCompleted() {
        this.cTasksCompleted++;
        Gdx.app.log(getClass().getSimpleName(), "Task " + cOperationNr + " succeeded.");
        AudioPlayer.getInstance().playAudio(cCompletedTaskSound);
        cLabelSubject.update("Good work!");
        cDelayInputTimer.reset();

        if (this.cTasksCompleted < MAX_TASKS) {
            cPrevOperationNr = cOperationNr;
            doTask();
        } else {
            clearEvent();
        }
    }

    /**
     * Clears the current event.
     */
    public void clearEvent() {
        super.dispose();
        TimerStore.getInstance().removeTimer(cDelayInputTimer);
    }

    @Override
    public void start() {
        TimerStore.getInstance().addTimer(cDelayInputTimer);
        cAccelMeter.filterGravity(true);
        doTask();
        cDelayInputTimer.stop();
    }

    /**
     * Checks if one of the individual tasks gets completed.
     *
     * @param accelData Vector containing the acceleration in the x,y and z direction.
     */
    public void processInput(final Vector3 accelData) {
        final float highestAccel = cAccelMeter.highestAccelerationComponent(accelData);
        final float delta = 2.5f;

        if (highestAccel >= delta) {
            Boolean success;
            switch (cOperationNr) {
                case MOVE_LEFT:
                    success = accelData.y >= delta;
                    break;
                case MOVE_RIGHT:
                    success = accelData.y <= -delta;
                    break;
                case MOVE_DOWN:
                    success = accelData.x <= -delta;
                    break;
                case MOVE_UP:
                    success = accelData.x >= delta;
                    break;
                case MOVE_AWAY:
                    success = accelData.z <= -delta;
                    break;
                case MOVE_TOWARDS:
                    success = accelData.z >= delta;
                    break;
                default:
                    success = false;
                    break;
            }
            if (success) {
                taskCompleted();
            } else {
                cDelayInputTimer.reset();
                cLabelSubject.update("Wrong!");
            }
        }
    }

    @Override
    public final int getReward() {
        return cTasksCompleted;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        Vector3 readings = cAccelMeter.update();
        //Done outside of the if to keep the resulting readings relevant. Needs testing
        if (!cDelayNewInput) {
            processInput(readings);
        }
    }
}

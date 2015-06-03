package cg.group4.game_logic.stroll.events;

import cg.group4.game_logic.StandUp;
import cg.group4.util.sensors.Accelerometer;
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
     * The string values belonging to each direction.
     */
    protected String[] cDirections = {"to the left", "to the right", "down", "up", "away from you", "towards you"};
    /**
     * Sound effect played when a task is completed.
     */
    protected final Sound cCompletedTaskSound;
    
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
        cCompletedTaskSound = Gdx.audio.newSound(Gdx.files.internal("sounds/completedTask.wav"));
        cTasksCompleted = 0;
        cPrevOperationNr = 0;

        cDelayInputStartObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                System.out.println("start");
                cDelayNewInput = true;
            }
        };

        cDelayInputStopObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                System.out.println("stop");
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
    public final void doTask() {
        do {
            cOperationNr = new Random().nextInt(6);
        } while (cOperationNr == cPrevOperationNr);

        cDelayInputTimer.reset();

    }

    /**
     * Gets called when one of the individual tasks gets completed.
     */
    public final void taskCompleted() {
        this.cTasksCompleted++;
        cCompletedTaskSound.play(1.0f);
        cLabelSubject.update("Good work!");
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
    public final void clearEvent() {
        super.dispose();
        TimerStore.getInstance().removeTimer(cDelayInputTimer);
    }

    @Override
    public void start() {
        TimerStore.getInstance().addTimer(cDelayInputTimer);
        cAccelMeter.filterGravity(true);
        doTask();
    }

    /**
     * Checks if one of the individual tasks gets completed.
     *
     * @param accelData Vector containing the acceleration in the x,y and z direction.
     */
    public final void processInput(final Vector3 accelData) {
        final float highestAccel = cAccelMeter.highestAccelerationComponent(accelData);
        final float delta = 2.5f;

        if (highestAccel >= delta) {
            System.out.println("jep");
            Boolean success = false;
            switch (cOperationNr) {
                case MOVE_LEFT:
                    success = accelData.y >= delta;
                    break;
                case MOVE_RIGHT:
                    success = accelData.y <= delta;
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
            }
            if (success) {
                Gdx.app.log(getClass().getSimpleName(), "Task " + cOperationNr + " succeeded.");
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
    public final void update(final Observable o, final Object arg) {
        Vector3 readings = cAccelMeter.update();
        //Done outside of the if to keep the resulting readings relevant. Needs testing
        if (!cDelayNewInput) {
            processInput(readings);
        }
    }
}

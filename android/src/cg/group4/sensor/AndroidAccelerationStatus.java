package cg.group4.sensor;

import android.hardware.SensorManager;
import cg.group4.util.sensors.AccelerationState;
import cg.group4.util.sensors.AccelerationStatus;
import cg.group4.util.subscribe.Subject;
import com.accellibandroid.Creator;
import com.accellibandroid.MovementEventListener;
import com.accellibandroid.Utilities.Movement;
import com.badlogic.gdx.Gdx;

/**
 * Gives back the acceleration status of the android device.
 *
 */
public class AndroidAccelerationStatus implements MovementEventListener,AccelerationStatus {

    /**
     * Tag for debugging & logging purposes.
     */
    private final String tag = this.getClass().getSimpleName();

    /**
     * Sensor manager which handles the sensor input.
     */
    protected SensorManager cSensorManager;

    /**
     * Creator for the accel library android.
     */
    protected Creator cCreator;

    protected Subject cUpdateMovementSubject;

    /**
     *  State to return to the application (which will be obtained from the accel library for android).
     *  Standard set to resting to avoid NullPointerExceptions when referenced from the stroll
     *  constructor. Changes accordingly to the movementChanged method below.
     */
    protected AccelerationState cAccelerationState = AccelerationState.RESTING;

    /**
     * Constructs the class for android devices which will return the state of the movement by the user.
     * @param sensorManager Sensor
     */
    public AndroidAccelerationStatus(final SensorManager sensorManager) {
        cSensorManager = sensorManager;
        cUpdateMovementSubject = new Subject();
        init();
    }

    /**
     * Initializes a creator for the accel library android and registers the movementChanged event listener to it.
     */
    public final void init() {
        cCreator = new Creator(cSensorManager);
        cCreator.registerListener(this);
    }

    /**
     * Will return the state of the accelerometer from the accel library android to the main application.
     * @return One of the four predeined states.
     */
    @Override
    public final AccelerationState getAccelerationState() {
        return cAccelerationState;
    }

    @Override
    public Subject getSubject() {
        return cUpdateMovementSubject;
    }


    /**
     * Event listener for the accelerometer.
     * @param movement Amount of movement.
     */
    @Override
    public final void movementChanged(final Movement movement) {
        if (movement.name().equals("WALKING")) {
            Gdx.app.debug(tag, "You are walking!");
            cAccelerationState = AccelerationState.WALKING;
        } else if (movement.name().equals("RUNNING")) {
            Gdx.app.debug(tag, "You are running!");
            cAccelerationState = AccelerationState.RUNNING;
        } else if (movement.name().equals("CHEATING")) {
            Gdx.app.debug(tag, "You are impossible!");
            cAccelerationState = AccelerationState.CHEATING;
        } else {
            Gdx.app.debug(tag, "You are resting!");
            cAccelerationState = AccelerationState.RESTING;
        }
        cUpdateMovementSubject.update(cAccelerationState);
    }
}

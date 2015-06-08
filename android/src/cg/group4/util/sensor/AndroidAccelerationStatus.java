package cg.group4.util.sensor;

import android.hardware.SensorManager;
import com.accellibandroid.Creator;
import com.accellibandroid.MovementEventListener;
import com.accellibandroid.Utilities.Movement;
import com.badlogic.gdx.Gdx;

/**
 * Gives back the acceleration status of the android device.
 */
public class AndroidAccelerationStatus extends AccelerationStatus implements MovementEventListener {

    /**
     * Tag for debugging & logging purposes.
     */
    private final String cTag = this.getClass().getSimpleName();

    /**
     * Sensor manager which handles the sensor input.
     */
    protected SensorManager cSensorManager;

    /**
     * Creator for the accel library android.
     */
    protected Creator cCreator;

    /**
     * State to return to the application (which will be obtained from the accel library android).
     */
    protected AccelerationState cAccelerationState;

    /**
     * Constructs the class for android devices which will return the state of the movement by the user.
     *
     * @param sensorManager Sensor
     */
    public AndroidAccelerationStatus(final SensorManager sensorManager) {
        cSensorManager = sensorManager;
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
     *
     * @return One of the four predeined states.
     */
    @Override
    public final AccelerationState getAccelerationState() {
        return cAccelerationState;
    }

    /**
     * Event listener for the accelerometer.
     *
     * @param movement Amount of movement.
     */
    @Override
    public final void movementChanged(final Movement movement) {
        if (movement.name().equals("WALKING")) {
            Gdx.app.debug(cTag, "You are walking!");
            cAccelerationState = AccelerationState.WALKING;
        } else if (movement.name().equals("RUNNING")) {
            Gdx.app.debug(cTag, "You are running!");
            cAccelerationState = AccelerationState.RUNNING;
        } else if (movement.name().equals("CHEATING")) {
            Gdx.app.debug(cTag, "You are impossible!");
            cAccelerationState = AccelerationState.CHEATING;
        } else {
            Gdx.app.debug(cTag, "You are resting!");
            cAccelerationState = AccelerationState.RESTING;
        }
    }
}

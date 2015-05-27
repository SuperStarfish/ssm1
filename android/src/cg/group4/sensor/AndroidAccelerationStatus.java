package cg.group4.sensor;

import android.hardware.SensorManager;
import com.accellibandroid.Creator;
import com.accellibandroid.MovementEventListener;
import com.accellibandroid.Utilities.Movement;
import com.badlogic.gdx.Gdx;

/**
 * Gives back the acceleration status of the android device.
 *
 */
public class AndroidAccelerationStatus extends AccelerationStatus implements MovementEventListener {

    /**
     * Tag for debugging & logging purposes.
     */
    private final String TAG = this.getClass().getSimpleName();

    /**
     * Sensor manager which handles the sensor input
     */
    SensorManager cSensorManager;

    /**
     * Creator for the accel library android.
     */
    Creator cCreator;

    /**
     *  State to return to the application (which will be obtained from the accel library android).
     */
    AccelerationState cAccelerationState;

    /**
     * Constructs the class for android devices which will return the state of the movement by the user.
     * @param sensorManager Sensor
     */
    public AndroidAccelerationStatus(SensorManager sensorManager) {
        cSensorManager = sensorManager;
        init();
    }

    /**
     * Initializes a creator for the accel library android and registers the movementChanged event listener to it.
     */
    public void init() {
        cCreator = new Creator(cSensorManager);
        cCreator.registerListener(this);
    }

    /**
     * Will return the state of the accelerometer from the accel library android to the main application.
     * @return One of the four predeined states.
     */
    @Override
    public AccelerationState getAccelerationState() {
        return cAccelerationState;
    }

    /**
     * Event listener for the accelerometer.
     * @param movement Amount of movement.
     */
    @Override
    public void movementChanged(Movement movement) {
        if(movement.name() == "WALKING") {
            Gdx.app.debug(TAG, "You are walking!");
            cAccelerationState = AccelerationState.WALKING;
        } else if(movement.name() == "RUNNING") {
            Gdx.app.debug(TAG, "You are running!");
            cAccelerationState = AccelerationState.RUNNING;
        } else if(movement.name() == "CHEATING") {
            Gdx.app.debug(TAG, "You are impossible!");
            cAccelerationState = AccelerationState.CHEATING;
        } else {
            Gdx.app.debug(TAG, "You are resting!");
            cAccelerationState = AccelerationState.RESTING;
        }
    }
}

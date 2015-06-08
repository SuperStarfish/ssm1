package cg.group4.util.sensor;

/**
 * Interface which will be the core module wrapper for the Accel Library Android.
 *
 * @author Martijn Gribnau
 */
public abstract class AccelerationStatus {

    /**
     * Returns defined states for the accelerometer.
     *
     * @return One of the four predefined acceleration states.
     */
    abstract AccelerationState getAccelerationState();
}

package cg.group4.sensor;

/**
 * Interface which will be the core module wrapper for the Accel Library Android.
 * @author
 */
public abstract class AccelerationStatus {

    /**
     * Returns defined states for the accelerometer.
     * @return One of the four predefined acceleration states.
     */
    public AccelerationState getAccelerationState() {

        return AccelerationState.RESTING;
    }
}

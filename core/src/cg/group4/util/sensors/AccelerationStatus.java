package cg.group4.util.sensors;

import cg.group4.util.subscribe.Subject;

/**
 * Interface which will be the core module wrapper for the Accel Library Android.
 * @author Martijn Gribnau
 */
public interface AccelerationStatus {

    /**
     * Returns defined states for the accelerometer.
     * @return One of the four predefined acceleration states.
     */
    AccelerationState getAccelerationState();

    Subject getSubject();
}

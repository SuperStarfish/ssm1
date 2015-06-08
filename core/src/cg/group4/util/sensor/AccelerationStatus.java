package cg.group4.util.sensor;


import cg.group4.data_structures.subscribe.Subject;

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
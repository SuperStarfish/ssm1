package cg.group4.sensor;


import cg.group4.util.sensors.AccelerationState;
import cg.group4.util.sensors.AccelerationStatus;

/**
 * Desktop implementation of thee acceleration status.
 */
public class DesktopAccelerationStatus implements AccelerationStatus {


    /**
     * Since a desktop has no acceleration, we here always return a default state.
     * The default state is AccelerationState.CHEATING
     * @return AccelerationState.CHEATING
     */
    @Override
    public final AccelerationState getAccelerationState() {
        return AccelerationState.CHEATING;
    }
}

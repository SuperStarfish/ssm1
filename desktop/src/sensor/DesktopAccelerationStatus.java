package sensor;


import cg.group4.sensor.AccelerationState;
import cg.group4.sensor.AccelerationStatus;

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
    public AccelerationState getAccelerationState() {
        return AccelerationState.CHEATING;
    }
}

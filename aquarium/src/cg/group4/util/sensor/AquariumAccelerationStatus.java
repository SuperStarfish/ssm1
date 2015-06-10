package cg.group4.util.sensor;

import cg.group4.data_structures.subscribe.Subject;

/**
 * Desktop implementation of thee acceleration status.
 */
public class AquariumAccelerationStatus implements AccelerationStatus {

    protected Subject uselessSubject = new Subject();

    /**
     * Since a desktop has no acceleration, we here always return a default state.
     * The default state is AccelerationState.CHEATING
     * @return AccelerationState.CHEATING
     */
    @Override
    public final AccelerationState getAccelerationState() {
        return AccelerationState.CHEATING;
    }

    @Override
    public final Subject getSubject() {
        return uselessSubject;
    }
}

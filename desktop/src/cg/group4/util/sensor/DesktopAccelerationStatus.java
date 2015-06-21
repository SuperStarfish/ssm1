package cg.group4.util.sensor;

import cg.group4.data_structures.subscribe.Subject;

/**
 * Desktop implementation of the acceleration status.
 */
public class DesktopAccelerationStatus implements AccelerationStatus {

    /**
     * Subject that does not do anything.
     */
    protected Subject cUselessSubject = new Subject();

    /**
     * Since a desktop has no acceleration, we here always return a default state.
     * The default state is AccelerationState.CHEATING
     *
     * @return AccelerationState.CHEATING
     */
    @Override
    public final AccelerationState getAccelerationState() {
        return AccelerationState.CHEATING;
    }

    @Override
    public final Subject getSubject() {
        return cUselessSubject;
    }
}

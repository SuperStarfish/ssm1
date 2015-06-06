package cg.group4.game_logic.stroll;

import cg.group4.GdxTestRunner;
import cg.group4.game_logic.StandUp;
import cg.group4.util.sensors.AccelerationState;
import cg.group4.util.sensors.AccelerationStatus;
import cg.group4.util.subscribe.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test class for the Stroll.java class.
 */
@RunWith(GdxTestRunner.class)
public class StrollTest {

    /**
     * Keeps track of the stroll object.
     */
    protected Stroll cStroll;

    /**
     * Setup method, gets run before every test.
     */
    @Before
    public void setUp() {
        StandUp.getInstance().setAccelerationStatus(new AccelerationStatus() {

            protected Subject tempsubject = new Subject();
            @Override
            public AccelerationState getAccelerationState() {
                return null;
            }

            @Override
            public Subject getSubject() {
                return tempsubject;
            }
        });
        cStroll = new Stroll();

    }

    /**
     * The chance of getting an event while walking should be the base value.
     */
    @Test
    public void getAmplifierWalkingTest() {
        assertEquals(1, cStroll.getAmplifier(AccelerationState.WALKING));
    }

    /**
     * The chance of getting an event while running should be doubled.
     */
    @Test
    public void getAmplifierRunningTest() {
        assertEquals(2, cStroll.getAmplifier(AccelerationState.RUNNING));
    }

    /**
     * You shouldn't get an event while cheating movement with your phone, so the chance should be 0.
     */
    @Test
    public void getAmplifierCheatingTest() {
        assertEquals(0, cStroll.getAmplifier(AccelerationState.CHEATING));
    }

    /**
     * You shouldn't get an event while you're not moving at all, so the chance should be 0.
     */
    @Test
    public void getAmplifierRestingTest() {
        assertEquals(0, cStroll.getAmplifier(AccelerationState.RESTING));
    }
}

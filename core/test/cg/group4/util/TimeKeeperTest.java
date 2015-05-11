package cg.group4.util;

import cg.group4.util.Timer.TimeKeeper;
import cg.group4.util.Timer.TimerListener;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for the TimeKeeper.
 */
public class TimeKeeperTest {
    public TimeKeeper timeKeeper;
    public TimerListener listener;

    /**
     * Creates a timeKeeper object for every test.
     * Setup mocks Gdx.app so debug is caught as well as a listener that can be used in tests.
     */
    @Before public void setup(){
        Gdx.app = mock(Application.class);
        timeKeeper = new TimeKeeper();
        listener = mock(TimerListener.class);
    }

    /**
     * Checks if listener is added correctly to the stroll interval list.
     */
    @Test public void testAddIntervalListener() {
        timeKeeper.addTimerListener(TimeKeeper.Timers.STROLL_INTERVAL, listener);
        assertEquals(1, timeKeeper.numberOfStrollIntervalListeners());
    }

    /**
     * Checks if a duplicate listener is not added. Prevents duplicate notify to a single listener.
     */
    @Test public void testAddDuplicateIntervalListener() {
        timeKeeper.addTimerListener(TimeKeeper.Timers.STROLL_INTERVAL, listener);
        timeKeeper.addTimerListener(TimeKeeper.Timers.STROLL_INTERVAL, listener);
        assertEquals(1, timeKeeper.numberOfStrollIntervalListeners());
    }

    /**
     * Checks if listener is added correctly to the stroll time list.
     */
    @Test public void testAddStrollListener() {
        timeKeeper.addTimerListener(TimeKeeper.Timers.STROLL_TIME, listener);
        assertEquals(1, timeKeeper.numberOfStrollTimerListeners());
    }

    /**
     * Checks if a duplicate listener is not added. Prevents duplicate notify to a single listener.
     */
    @Test public void testAddDuplicateStrollListener() {
        timeKeeper.addTimerListener(TimeKeeper.Timers.STROLL_TIME, listener);
        timeKeeper.addTimerListener(TimeKeeper.Timers.STROLL_TIME, listener);
        assertEquals(1, timeKeeper.numberOfStrollTimerListeners());
    }
}

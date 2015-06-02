package cg.group4.util.timer;

import cg.group4.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;

/**
 * Tests for the timeStore class.
 */
@RunWith(GdxTestRunner.class)
public class TimerStoreTest {
    /**
     * The timeStore that will be tested upon.
     */
    protected TimerStore cTimerStore;
    /**
     * Timer to test with.
     */
    protected Timer cTimer;

    /**
     * Creates the needed objects for the tests.
     */
    @Before
    public final void setUp() {
        cTimerStore = new TimerStore();
        cTimer = new Timer("TEST", 60);
    }

    /**
     * Tests the initialization of the timer store.
     */
    @Test
    public final void testInitGlobalTimers() {
        assertEquals(Timer.Global.values().length, cTimerStore.cTimers.size());
    }

    /**
     * Tests the adding of a new timer to the timer store.
     */
    @Test
    public final void testAddTimer() {
        int size = cTimerStore.cTimers.size();
        cTimerStore.addTimer(cTimer);
        assertEquals(size + 1, cTimerStore.cTimers.size());
    }

    /**
     * Tests adding of two timers under the same name.
     */
    @Test
    public final void testAddDuplicateTimer() {
        cTimerStore.addTimer(cTimer);
        int size = cTimerStore.cTimers.size();
        cTimerStore.addTimer(new Timer(cTimer.getName(), 5));
        assertEquals(size, cTimerStore.cTimers.size());
    }

    /**
     * Tests the retrieval of timers from the timer store.
     */
    @Test
    public final void testGetTimer() {
        cTimerStore.addTimer(cTimer);
        assertTrue(cTimer.equals(cTimerStore.getTimer(cTimer.cName)));
    }

    /**
     * Tests the retrieval of a non existing timer.
     */
    @Test
    public final void testGetTimerNull() {
        cTimerStore.addTimer(cTimer);
        assertNull(cTimerStore.getTimer("TAD"));
    }

    /**
     * Tests the removal of a timer.
     */
    @Test
    public final void testRemoveTimer() {
        cTimerStore.addTimer(cTimer);
        assertTrue(cTimerStore.cTimers.containsValue(cTimer));
        int size = cTimerStore.getTimeKeeper().getTimerSubject().countObservers();
        cTimerStore.removeTimer(cTimer);
        assertFalse(cTimerStore.cTimers.containsValue(cTimer));
        assertEquals(size - 1, cTimerStore.getTimeKeeper().getTimerSubject().countObservers());
    }
}

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
    protected TimerStore timerStore;
    /**
     * Timer to test with.
     */
    protected Timer timer;

    /**
     * Creates the needed objects for the tests.
     */
    @Before
    public final void setUp() {
        timerStore = new TimerStore();
        timer = new Timer("TEST", 60);
    }

    /**
     * Tests the initialization of the timer store.
     */
    @Test
    public final void testInitGlobalTimers() {
        assertEquals(Timer.Global.values().length, timerStore.cTimers.size());
    }

    /**
     * Tests the adding of a new timer to the timer store.
     */
    @Test
    public final void testAddTimer() {
        int size = timerStore.cTimers.size();
        timerStore.addTimer(timer);
        assertEquals(size + 1, timerStore.cTimers.size());
    }

    /**
     * Tests adding of two timers under the same name.
     */
    @Test
    public final void testAddDuplicateTimer() {
        timerStore.addTimer(timer);
        int size = timerStore.cTimers.size();
        timerStore.addTimer(new Timer(timer.getName(), 5));
        assertEquals(size, timerStore.cTimers.size());
    }

    /**
     * Tests the retrieval of timers from the timer store.
     */
    @Test
    public final void testGetTimer() {
        timerStore.addTimer(timer);
        assertTrue(timer.equals(timerStore.getTimer(timer.cName)));
    }

    /**
     * Tests the retrieval of a non existing timer.
     */
    @Test
    public final void testGetTimerNull() {
        timerStore.addTimer(timer);
        assertNull(timerStore.getTimer("TAD"));
    }

    /**
     * Tests the removal of a timer.
     */
    @Test
    public final void testRemoveTimer() {
        timerStore.addTimer(timer);
        assertTrue(timerStore.cTimers.containsValue(timer));
        int size = timerStore.getTimeKeeper().getTimerSubject().countObservers();
        timerStore.removeTimer(timer);
        assertFalse(timerStore.cTimers.containsValue(timer));
        assertEquals(size - 1, timerStore.getTimeKeeper().getTimerSubject().countObservers());
    }
}

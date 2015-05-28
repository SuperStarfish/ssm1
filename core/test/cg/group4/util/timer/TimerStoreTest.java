package cg.group4.util.timer;

import cg.group4.GdxTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class TimerStoreTest {
    TimerStore timerStore;
    Timer timer;

    @Before
    public void setUp() {
        timerStore = TimerStore.getInstance();
        timer = new Timer("TEST", 60);
    }

    @Test
    public void testInitGlobalTimers() {
        assertEquals(Timer.Global.values().length, timerStore.cTimers.size());
    }

    @Test
    public void testAddTimer() {
        int size = timerStore.cTimers.size();
        timerStore.addTimer(timer.getName(), timer);
        assertEquals(size + 1, timerStore.cTimers.size());
    }

    @Test
    public void testAddDuplicateTimer() {
        timerStore.addTimer(timer.getName(), timer);
        int size = timerStore.cTimers.size();
        timerStore.addTimer(timer.getName(), new Timer(timer.getName(), 5));
        assertEquals(size, timerStore.cTimers.size());
    }

    @Test
    public void testGetTimer() {
        timerStore.addTimer(timer.getName(), timer);
        assertTrue(timer.equals(timerStore.getTimer(timer.cName)));
    }

    @Test
    public void testGetTimerNull() {
        timerStore.addTimer(timer.getName(), timer);
        assertNull(timerStore.getTimer("TAD"));
    }

    @After
    public void tearDown() {
        TimerStore.cInstance = null;
    }
}

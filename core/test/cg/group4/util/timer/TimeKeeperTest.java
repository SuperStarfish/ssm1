package cg.group4.util.timer;

import cg.group4.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class TimeKeeperTest {
    TimeKeeper timeKeeper;
    Timer timer;

    @Before
    public void setUp() {
        timeKeeper = TimeKeeper.getInstance();
        timeKeeper.cTimers = new HashSet<Timer>();
        timer = new Timer("TEST", 60);
    }

    @Test
    public void testInitGlobalTimers() {
        int count = TimeKeeper.getInstance().cTimers.size();
        timeKeeper.init();
        assertEquals(count + Timer.Global.values().length, TimeKeeper.getInstance().cTimers.size());
    }

    @Test
    public void testAddTimer() {
        int size = timeKeeper.cTimers.size();
        timeKeeper.addTimer(new Timer("Test2", 5));
        assertEquals(size + 1, timeKeeper.cTimers.size());
    }

    @Test
    public void testAddDuplicateTimer() {
        timeKeeper.addTimer(new Timer("Test2", 0));
        int size = timeKeeper.cTimers.size();
        timeKeeper.addTimer(new Timer("Test2", 5));
        assertEquals(size, timeKeeper.cTimers.size());
    }

    @Test
    public void testGetTimer() {
        timeKeeper.addTimer(timer);
        assertTrue(timer.equals(timeKeeper.getTimer(timer.cName)));
    }

    @Test
    public void testGetTimerNull() {
        timeKeeper.addTimer(timer);
        assertNull(timeKeeper.getTimer("TAD"));
    }

    @Test
    public void testNotifyStop() {
        timeKeeper.addTimer(timer);
        assertNull(timeKeeper.getTimer("TAD"));
    }
}

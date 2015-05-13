package cg.group4.util.timer;

import cg.group4.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class TimeKeeperTest {
    TimeKeeper timeKeeper = TimeKeeper.getInstance();
    Timer timer;

    @Before
    public void setUp(){
        timeKeeper.c_timers = new HashSet<Timer>();
        timer = new Timer("TEST", 60);
    }

    @Test
    public void testInitGlobalTimers(){
        int count = TimeKeeper.getInstance().c_timers.size();
        timeKeeper.init();
        assertEquals(count + Timer.Global.values().length, TimeKeeper.getInstance().c_timers.size());
    }

    @Test
    public void testAddTimer(){
        int size = timeKeeper.c_timers.size();
        timeKeeper.addTimer(new Timer("Test2", 5));
        assertEquals(size + 1, timeKeeper.c_timers.size());
    }

    @Test
    public void testContainsTimerTrue(){
        assertTrue(timeKeeper.containsTimer("TEST"));
    }

    @Test
    public void testContainsTimerFalse(){
        assertFalse(timeKeeper.containsTimer("TEST2"));
    }

    @Test
    public void testGetTimer(){
        timeKeeper.addTimer(timer);
        assertTrue(timer.equals(timeKeeper.getTimer(timer.c_name)));
    }

    @Test
    public void testGetTimerNull(){
        timeKeeper.addTimer(timer);
        assertNull(timeKeeper.getTimer("TAD"));
    }

    @Test
    public void testUpdate(){
        Timer timer = mock(Timer.class);
        timeKeeper.addTimer(timer);
        timeKeeper.c_previousTick = System.currentTimeMillis() - 2000;
        timeKeeper.update();
        verify(timer, times(1)).tick(Mockito.anyLong());
    }
}

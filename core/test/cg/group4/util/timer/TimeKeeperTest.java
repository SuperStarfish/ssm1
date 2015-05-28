package cg.group4.util.timer;

import cg.group4.GdxTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(GdxTestRunner.class)
public class TimeKeeperTest {
    TimeKeeper timeKeeper;
    Timer timer;

    @Before
    public void setUp() {
        timeKeeper = new TimeKeeper();
        timer = new Timer("TEST", 60);
    }

    @Test
    public void testGetTimerSubject() {
        assertEquals(timeKeeper.getTimerSubject(), timeKeeper.cTimerSubject);
    }


}

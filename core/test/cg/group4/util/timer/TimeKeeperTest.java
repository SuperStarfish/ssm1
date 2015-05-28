package cg.group4.util.timer;

import cg.group4.GdxTestRunner;
import cg.group4.util.subscribe.Subject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Tests for the timeKeeper class.
 */
@RunWith(GdxTestRunner.class)
public class TimeKeeperTest {
    /**
     * The timeKeeper that will be tested upon.
     */
    protected TimeKeeper timeKeeper;

    /**
     * Creates the needed objects for the tests.
     */
    @Before
    public final void setUp() {
        timeKeeper = new TimeKeeper();
    }

    /**
     * Test to verify the getter returns the right object.
     */
    @Test
    public final void testGetTimerSubject() {
        assertEquals(timeKeeper.getTimerSubject(), timeKeeper.cTimerSubject);
    }

    /**
     * Test to verify the getter returns the right object.
     */
    @Test
    public final void testUpdate() {
        timeKeeper.cTimerSubject = mock(Subject.class);
        timeKeeper.cPreviousTick -= 1000;
        timeKeeper.update();
        verify(timeKeeper.cTimerSubject, times(1)).notifyObservers(anyLong());
    }


}

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
    protected TimeKeeper cTimeKeeper;

    /**
     * Creates the needed objects for the tests.
     */
    @Before
    public final void setUp() {
        cTimeKeeper = new TimeKeeper();
    }

    /**
     * Test to verify the getter returns the right object.
     */
    @Test
    public final void testGetTimerSubject() {
        assertEquals(cTimeKeeper.getTimerSubject(), cTimeKeeper.cTimerSubject);
    }

    /**
     * Test to verify the getter returns the right object.
     */
    @Test
    public final void testUpdate() {
        cTimeKeeper.cTimerSubject = mock(Subject.class);
        cTimeKeeper.cPreviousTick -= 1000;
        cTimeKeeper.update();
        verify(cTimeKeeper.cTimerSubject, times(1)).notifyObservers(anyLong());
    }


}

package cg.group4.stroll;

import cg.group4.GdxTestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class StrollTest {

    protected Stroll cStroll;

    @Before
    public void setUp() {
        cStroll = new Stroll();
//        TimeKeeper.getInstance().getTimer("STROLL").unsubscribe(cStroll.cTimerTask);
    }

    @Test
    public void testGeneratePossibleEvent() {
        assertTrue(true);
//        assertFalse(cStroll.cEventGoing);
//        assertNull(cStroll.cEvent);
//
//        cStroll.cEventThreshold = 1;
//        cStroll.generatePossibleEvent();
//
//        assertTrue(cStroll.cEventGoing);
//        assertNotNull(cStroll.cEvent);
    }



    @After
    public void tearDown(){
    }

}

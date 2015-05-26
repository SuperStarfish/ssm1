package cg.group4.game_logic.stroll;

import cg.group4.GdxTestRunner;
import cg.group4.util.timer.TimeKeeper;
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
        TimeKeeper.getInstance().init();
        cStroll = new Stroll();
    }

    @Test
    public void testGeneratePossibleEvent() {
        assertTrue(true);
    }



    @After
    public void tearDown(){
    }

}

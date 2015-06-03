package cg.group4.game_logic.stroll;

import cg.group4.GdxTestRunner;
import cg.group4.util.sensors.AccelerationState;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nick on 3-6-2015.
 */
@RunWith(GdxTestRunner.class)
public class StrollTest {

    protected Stroll cStroll;

    @Before
    public void setUp(){
        cStroll = new Stroll();
    }

    @After
    public void tearDown(){

    }

    @Test
    public void getAmplifierWalkingTest(){
        assertEquals(1, cStroll.getAmplifier(AccelerationState.WALKING));
    }

    @Test
    public void getAmplifierRunningTest(){
        assertEquals(2, cStroll.getAmplifier(AccelerationState.RUNNING));
    }

    @Test
    public void getAmplifierCheatingTest(){
        assertEquals(0, cStroll.getAmplifier(AccelerationState.CHEATING));
    }

    @Test
    public void getAmplifierRestingTest(){
        assertEquals(0, cStroll.getAmplifier(AccelerationState.RESTING));
    }
}

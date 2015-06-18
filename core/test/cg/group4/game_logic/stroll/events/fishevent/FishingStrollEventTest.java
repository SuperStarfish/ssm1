package cg.group4.game_logic.stroll.events.fishevent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cg.group4.data_structures.subscribe.Subject;
import cg.group4.util.sensor.Accelerometer;
import cg.group4.util.timer.Timer;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxNativesLoader;

/**
 * Tests the FishingStrollEvent.java class and all its state classes.
 */
public class FishingStrollEventTest {

    /**
     * Value for the z axis to get out of the castForward state.
     */
    protected static final float CAST_FORWARD_SUCCESS = 4f;

    /**
     * Value for the z axis to stay in the castForward state.
     */
    protected static final float CAST_FORWARD_FAIL = 3f;

    /**
     * Value for the z axis to get out of the reelIn state.
     */
    protected static final float REEL_IN_SUCCESS = -3f;

    /**
     * Value for the z axis to stay in the reelIn state.
     */
    protected static final float REEL_IN_FAIL = -2f;

    /**
     * Instance of the stroll to be tested.
     */
    protected FishingStrollEvent cTestEvent;

    /**
     * Spy to check for methodcalls.
     */
    protected FishingStrollEvent cSpyEvent;

    /**
     * SetUp to run before each test.
     */
    @Before
    public void setUp() {
        cTestEvent = new FishingStrollEvent();
        cSpyEvent = Mockito.spy(cTestEvent);
        Mockito.doNothing().when(cSpyEvent).clearEvent();
    }

    /**
     * The getter should return the amount of rewards.
     */
    @Test
    public void getRewardTest() {
        assertEquals(FishingStrollEvent.REWARDS, cTestEvent.getReward());
    }

    /**
     * The event should be disposed/cleared when you complete it.
     */
    @Test
    public void eventCompletedTest() {
        cSpyEvent.eventCompleted();

        Mockito.verify(cSpyEvent).clearEvent();
    }

    /**
     * The event should be completed when you pull in the fish.
     */
    @Test
    public void reelInStateProcessInputTest() {
        ReelInState state = new ReelInState(cSpyEvent);
        cSpyEvent.cState = state;
        Mockito.doNothing().when(cSpyEvent).eventCompleted();
        state.processInput(new Vector3(0, 0, REEL_IN_SUCCESS));

        Mockito.verify(cSpyEvent).eventCompleted();
    }

    /**
     * The event should not be completed when you don't pull in the fish hard enough.
     */
    @Test
    public void reelInStateProcessInputFailTest() {
        ReelInState state = new ReelInState(cSpyEvent);
        cSpyEvent.cState = state;
        Mockito.doNothing().when(cSpyEvent).eventCompleted();
        state.processInput(new Vector3(0, 0, REEL_IN_FAIL));

        Mockito.verify(cSpyEvent, Mockito.times(0)).eventCompleted();
    }

    /**
     * The state should change when you swing hard enough.
     */
    @Test
    public void castForwardStateProcessInputTest() {
        CastForwardState state = new CastForwardState(cSpyEvent);
        cSpyEvent.cState = state;
        state.processInput(new Vector3(0, 0, CAST_FORWARD_SUCCESS));

        assertTrue(cSpyEvent.cState instanceof WaitState);
    }

    /**
     * The state shouldn't change when you swing not hard enough.
     */
    @Test
    public void castForwardStateProcessInputFailTest() {
        CastForwardState state = new CastForwardState(cSpyEvent);
        cSpyEvent.cState = state;
        state.processInput(new Vector3(0, 0, CAST_FORWARD_FAIL));

        assertFalse(cSpyEvent.cState instanceof WaitState);
    }

    /**
     * The timer shouldn't be reset if nothing happens.
     */
    @Test
    public void waitStateProcessInputTest() {
        WaitState state = new WaitState(cSpyEvent);
        state.cFishTimer = Mockito.mock(Timer.class);
        Mockito.doNothing().when(state.cFishTimer).reset();
        cSpyEvent.cState = state;
        state.processInput(new Vector3(0, 0, 0));

        Mockito.verify(state.cFishTimer, Mockito.times(0)).reset();
    }

    /**
     * The timer should reset when there is too much movement in the waitstate.
     */
    @Test
    public void waitStateProcessInputFailTest() {
        WaitState state = new WaitState(cSpyEvent);
        state.cFishTimer = Mockito.mock(Timer.class);
        Mockito.doNothing().when(state.cFishTimer).reset();
        cSpyEvent.cState = state;
        state.processInput(new Vector3(2f, 2f, 2f));

        Mockito.verify(state.cFishTimer, Mockito.times(1)).reset();
    }
    
    /**
     * Every game cycle the FishEventState should be updated with the new
     * input from the acccelerometer.
     */
    @Test
    public void updateTest() {
    	Accelerometer accelMock = Mockito.mock(Accelerometer.class);
    	Mockito.when(accelMock.update()).thenReturn(new Vector3(1f, 1f, 1f));
    	FishEventState stateMock = Mockito.mock(FishEventState.class);
    	cTestEvent.cState = stateMock;
    	cTestEvent.cAccelMeter = accelMock;
    	
       	cTestEvent.update(null, null);
    	Mockito.verify(accelMock, Mockito.times(1)).update();
    	Mockito.verify(stateMock, Mockito.timeout(1)).processInput(new Vector3(1f, 1f, 1f));
    }
    
    /**
     * The accelerometer settings must be done and the event should 
     * switch to the CastForwardState.
     */
    @Test
    public void startTest() {
    	Accelerometer accelMock = Mockito.mock(Accelerometer.class);
    	cTestEvent.cAccelMeter = accelMock;
    	
    	cTestEvent.start();
    	Mockito.verify(accelMock, Mockito.times(1)).filterGravity(Mockito.eq(true));
    	assertTrue(cTestEvent.cState instanceof CastForwardState);
    }
}

package cg.group4.game_logic.stroll.events.fishevent;

import cg.group4.data_structures.subscribe.Subject;
import cg.group4.util.timer.Timer;
import com.badlogic.gdx.math.Vector3;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FishingStrollEventTest {

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

    @Test
    public void getRewardTest() {
        assertEquals(FishingStrollEvent.REWARDS, cTestEvent.getReward());
    }

    @Test
    public void eventCompletedTest() {
        cSpyEvent.eventCompleted();

        Mockito.verify(cSpyEvent).clearEvent();
    }

    @Test
    public void reelInStateProcessInputTest() {
        ReelInState state = new ReelInState(cSpyEvent);
        cSpyEvent.cState = state;
        Mockito.doNothing().when(cSpyEvent).eventCompleted();
        state.processInput(new Vector3(0, 0, -3f));

        Mockito.verify(cSpyEvent).eventCompleted();
    }

    @Test
    public void reelInStateProcessInputFailTest() {
        ReelInState state = new ReelInState(cSpyEvent);
        cSpyEvent.cState = state;
        Mockito.doNothing().when(cSpyEvent).eventCompleted();
        state.processInput(new Vector3(0,0,-2f));

        Mockito.verify(cSpyEvent, Mockito.times(0)).eventCompleted();
    }

    @Test
    public void castForwardStateProcessInputTest() {
        CastForwardState state = new CastForwardState(cSpyEvent);
        cSpyEvent.cState = state;
        state.processInput(new Vector3(0, 0, 4f));

        assertTrue(cSpyEvent.cState instanceof WaitState);
    }

    @Test
    public void castForwardStateProcessInputFailTest() {
        CastForwardState state = new CastForwardState(cSpyEvent);
        cSpyEvent.cState = state;
        state.processInput(new Vector3(0, 0, 3f));

        assertFalse(cSpyEvent.cState instanceof WaitState);
    }

    @Test
    public void waitStateProcessInputTest() {
        WaitState state = new WaitState(cSpyEvent);
        state.cFishTimer = Mockito.mock(Timer.class);
        Mockito.doNothing().when(state.cFishTimer).reset();
        cSpyEvent.cState = state;
        state.processInput(new Vector3(0, 0, 0));

        Mockito.verify(state.cFishTimer, Mockito.times(0)).reset();
    }

    @Test
    public void waitStateProcessInputFailTest() {
        WaitState state = new WaitState(cSpyEvent);
        state.cFishTimer = Mockito.mock(Timer.class);
        Mockito.doNothing().when(state.cFishTimer).reset();
        cSpyEvent.cState = state;
        state.processInput(new Vector3(2f, 2f, 2f));

        Mockito.verify(state.cFishTimer, Mockito.times(1)).reset();
    }

}

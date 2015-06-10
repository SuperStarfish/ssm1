package cg.group4.game_logic.stroll.events.fishevent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

/**
 * Created by Nick on 10-6-2015.
 */
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
    }

    @Test
    public void getRewardTest() {
        assertEquals(FishingStrollEvent.REWARDS, cTestEvent.getReward());
    }
}

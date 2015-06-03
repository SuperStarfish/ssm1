package cg.group4.game_logic.stroll;

import cg.group4.GdxTestRunner;
import cg.group4.game_logic.StandUp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Tests for the class Stroll.
 */
@RunWith(GdxTestRunner.class)
public class StrollTest {

    /**
     * Instance of the stroll to be tested.
     */
    protected Stroll cStroll;

    /**
     * SetUp to run before each test.
     */
    @Before
    public void setUp() {
        cStroll = new Stroll();
    }

    /**
     * Tests the functionality of the stroll stop observer.
     */
    @Test
    public void testStrollStopObserver() {
        cStroll.cStrollStopObserver.update(null, null);
        assertTrue(cStroll.cFinished);
    }

    /**
     * Good weather test for method update.
     */
    @Test
    public void testUpdateGood() {
        cStroll = spy(cStroll);
        doNothing().when(cStroll).generatePossibleEvent();

        cStroll.update(null, null);

        verify(cStroll).generatePossibleEvent();
    }

    /**
     * Bad weather test for method update.
     */
    @Test
    public void testUpdateBad() {
        cStroll = spy(cStroll);
        cStroll.cEventGoing = true;

        cStroll.update(null, null);

        verify(cStroll, never()).generatePossibleEvent();
    }

//    Can not be tested because events are not yet decoupled.
//    /**
//     * Test the generation of an event.
//     */
//    @Test
//    public void testGeneratePossibleEvent() {
//        cStroll.cEventThreshold = 1f;
//        cStroll.cNewEventSubject = spy(cStroll.cNewEventSubject);
//
//        cStroll.generatePossibleEvent();
//
//        assertTrue(cStroll.cEventGoing);
//        assertNotNull(cStroll.cEvent);
//        verify(cStroll.cNewEventSubject).update(cStroll.cEvent);
//    }

    /**
     * Tests the mechanics of finishing an event.
     */
    @Test
    public void testEventFinished() {
        cStroll.cEndEventSubject = spy(cStroll.cEndEventSubject);
        final int bonusRewards = 5;
        int rewards = cStroll.cRewards;

        cStroll.eventFinished(bonusRewards);

        verify(cStroll.cEndEventSubject).update(bonusRewards);
        assertNull(cStroll.cEvent);
        assertFalse(cStroll.cEventGoing);
        assertEquals(rewards + bonusRewards, cStroll.cRewards);
    }

    /**
     * Tests the mechanics of finishing an event when the stroll is done.
     */
    @Test
    public void testEventFinishedDone() {
        cStroll = spy(cStroll);
        cStroll.cFinished = true;

        cStroll.eventFinished(0);

        verify(cStroll).done();
    }

    /**
     * Tests for the completion of a stroll.
     */
    @Test
    public void testDone() {
        cStroll.cEndStrollSubject = spy(cStroll.cEndStrollSubject);

        cStroll.done();

        verify(cStroll.getEndStrollSubject()).update(cStroll.cRewards);
        assertEquals(0, cStroll.getEndStrollSubject().countObservers());
        assertNull(StandUp.getInstance().getStroll());
    }

    /**
     * Tests the retrieval of the EndStrollSubject.
     */
    @Test
    public void testGetEndStrollSubject() {
        assertEquals(cStroll.cEndStrollSubject, cStroll.getEndStrollSubject());
    }

    /**
     * Tests the retrieval of the NewEventSubject.
     */
    @Test
    public void testGetNewEventSubject() {
        assertEquals(cStroll.cNewEventSubject, cStroll.getNewEventSubject());
    }

    /**
     * Tests the retrieval of the EndEventSubject.
     */
    @Test
    public void testGetEndEventSubject() {
        assertEquals(cStroll.cEndEventSubject, cStroll.getEndEventSubject());
    }
}
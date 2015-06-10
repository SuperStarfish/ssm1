package cg.group4.game_logic.stroll.events;

import cg.group4.GdxTestRunner;
import cg.group4.util.sensors.Accelerometer;
import com.badlogic.gdx.math.Vector3;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Observable;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Class that tests the basic stroll event.
 */
@RunWith(GdxTestRunner.class)
public class TestStrollEventTest {

    /**
     * Instance of the stroll to be tested.
     */
    protected TestStrollEvent cTestEvent;

    /**
     * Spy to check for methodcalls.
     */
    protected TestStrollEvent cSpyEvent;

    /**
     * SetUp to run before each test.
     */
    @Before
    public void setUp() {
        cTestEvent = new TestStrollEvent();
        cSpyEvent = Mockito.spy(cTestEvent);
    }

    /**
     * The event is not completed when we have not completed enough tasks.
     */
    @Test
    public void taskCompletedNotComplete() {
        cSpyEvent.cTasksCompleted = 1;

        cSpyEvent.taskCompleted();

        Mockito.verify(cSpyEvent).doTask();
    }

    /**
     * The event is completed when we have completed enough tasks.
     */
    @Test
    public void taskCompletedComplete() {
        cSpyEvent.cTasksCompleted = 20;
        Mockito.doNothing().when(cSpyEvent).clearEvent();

        cSpyEvent.taskCompleted();

        Mockito.verify(cSpyEvent).clearEvent();
    }

    /**
     * You do a task when started.
     */
    @Test
    public void startTest() {
        cSpyEvent.start();

        Mockito.verify(cSpyEvent).doTask();
    }

    /**
     * Should return the rewards of the event.
     */
    @Test
    public void getRewardTest() {
        cSpyEvent.cTasksCompleted = 10;
        assertEquals(10, cSpyEvent.getReward());
    }

    /**
     * You should not process input when you have done that at least a second ago.
     */
    @Test
    public void updateNotAcceptingInputTest() {
        cSpyEvent.cDelayNewInput = true;

        cSpyEvent.update(Mockito.mock(Observable.class), Mockito.mock(Object.class));

        Mockito.verify(cSpyEvent,Mockito.times(0)).processInput(Mockito.any(Vector3.class));
    }

    /**
     * You should process input when you haven't done that for over 1 second.
     */
    @Test
    public void updateAcceptingInputTest() {
        cSpyEvent.cDelayNewInput = false;

        cSpyEvent.update(Mockito.mock(Observable.class), Mockito.mock(Object.class));

        Mockito.verify(cSpyEvent).processInput(Mockito.any(Vector3.class));
    }

    /**
     * The next operation shouldn't be the same as the previous.
     */
    @Test
    public void doTaskTest() {
        Random random = new Random();
        cTestEvent.cPrevOperationNr = random.nextInt(6);
        cTestEvent.cOperationNr = cTestEvent.cPrevOperationNr;

        cTestEvent.doTask();

        assertNotEquals(cTestEvent.cPrevOperationNr, cTestEvent.cOperationNr);
    }

    /**
     * Tests if it succesfully clears the event.
     */
    @Test
    public void clearEventTest() {
        cSpyEvent.clearEvent();
    }
}

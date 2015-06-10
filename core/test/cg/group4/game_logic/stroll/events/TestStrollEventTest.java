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

    protected TestStrollEvent cSpyEvent;

    protected Accelerometer cAccelMock;

    protected Vector3 cTestVector;

    protected Vector3 cTestVector2;

    /**
     * SetUp to run before each test.
     */
    @Before
    public void setUp() {
        cTestEvent = new TestStrollEvent();
        cSpyEvent = Mockito.spy(cTestEvent);
        cAccelMock = Mockito.mock(Accelerometer.class);
        cTestVector = new Vector3().set(1f,1f,1f);
        cTestVector2 = new Vector3().set(3f,3f,3f);
    }

    @Test
    public void taskCompletedNotComplete() {
        cSpyEvent.cTasksCompleted = 1;

        cSpyEvent.taskCompleted();

        Mockito.verify(cSpyEvent).doTask();
    }

    @Test
    public void taskCompletedComplete() {
        cSpyEvent.cTasksCompleted = 20;
        Mockito.doNothing().when(cSpyEvent).clearEvent();

        cSpyEvent.taskCompleted();

        Mockito.verify(cSpyEvent).clearEvent();
    }

    @Test
    public void startTest() {
        cSpyEvent.start();

        Mockito.verify(cSpyEvent).doTask();
    }

    @Test
    public void getRewardTest() {
        cSpyEvent.cTasksCompleted = 10;
        assertEquals(10, cSpyEvent.getReward());
    }

    @Test
    public void updateNotAcceptingInputTest() {
        cSpyEvent.cDelayNewInput = true;

        cSpyEvent.update(Mockito.mock(Observable.class), Mockito.mock(Object.class));

        Mockito.verify(cSpyEvent,Mockito.times(0)).processInput(Mockito.any(Vector3.class));
    }

    @Test
    public void updateAcceptingInputTest() {
        cSpyEvent.cDelayNewInput = false;

        cSpyEvent.update(Mockito.mock(Observable.class), Mockito.mock(Object.class));

        Mockito.verify(cSpyEvent).processInput(Mockito.any(Vector3.class));
    }

    @Test
    public void doTaskTest() {
        Random random = new Random();
        cTestEvent.cPrevOperationNr = random.nextInt(6);
        cTestEvent.cOperationNr = cTestEvent.cPrevOperationNr;

        cTestEvent.doTask();

        assertNotEquals(cTestEvent.cPrevOperationNr, cTestEvent.cOperationNr);
    }
}

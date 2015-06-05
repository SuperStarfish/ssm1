package cg.group4.game_logic.stroll.events;

import cg.group4.GdxTestRunner;
import cg.group4.util.sensors.Accelerometer;
import com.badlogic.gdx.math.Vector3;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import java.util.Observable;

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
     * SetUp to run before each test.
     */
    @Before
    public void setUp() {
        cTestEvent = new TestStrollEvent();
    }

    @Test
    public void taskCompletedNotComplete() {
        cTestEvent.cTasksCompleted = 1;
        TestStrollEvent spy = Mockito.spy(cTestEvent);

        spy.taskCompleted();
        Mockito.verify(spy).doTask();
    }

    @Test
    public void taskCompletedComplete() {
        cTestEvent.cTasksCompleted = 20;
        TestStrollEvent spy = Mockito.spy(cTestEvent);
        Mockito.doNothing().when(spy).clearEvent();

        spy.taskCompleted();
        Mockito.verify(spy).clearEvent();
    }
}

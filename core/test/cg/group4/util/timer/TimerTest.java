package cg.group4.util.timer;

import cg.group4.GdxTestRunner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Observable;
import java.util.Observer;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for the timer class.
 */
@RunWith(GdxTestRunner.class)
public class TimerTest {

    /**
     * Timer that will be tested upon.
     */
    protected Timer cTimer;

    /**
     * Initializes objects for a test.
     */
    @Before
    public final void setUp() {
        cTimer = new Timer("TEST", 5);
    }

    /**
     * Test to verify the getter returns the right object.
     */
    @Test
    public final void testGetName() {
        assertEquals(cTimer.cName, cTimer.getName());
    }

    /**
     * Test to verify the getter returns the right object.
     */
    @Test
    public final void testGetRemainingTime() {
        assertEquals(cTimer.cFinishTime, cTimer.getFinishTime());
    }

    /**
     * Test to verify the getter returns the right object.
     */
    @Test
    public final void testGetStartSubject() {
        assertEquals(cTimer.cStartSubject, cTimer.getStartSubject());
    }

    /**
     * Test to verify the getter returns the right object.
     */
    @Test
    public final void testGetStopSubject() {
        assertEquals(cTimer.cStopSubject, cTimer.getStopSubject());
    }

    /**
     * Test to verify the getter returns the right object.
     */
    @Test
    public final void testGetTickSubject() {
        assertEquals(cTimer.cTickSubject, cTimer.getTickSubject());
    }

    /**
     * Verifies that a timer stops when a tick happens after the timers duration has past.
     */
    @Test
    public final void testOnTickWhenFinished() {
        assertTrue(cTimer.cRunning);
        long timeStamp = System.currentTimeMillis();
        cTimer.cFinishTime = timeStamp - cTimer.cDuration;
        cTimer.tick(timeStamp);
        assertFalse(cTimer.cRunning);
    }

    /**
     * Verifies that a timer does not do anything after the timer has stopped.
     */
    @Test
    public final void testTickWhenNotRunning() {
        cTimer.stop();
        Observer timerObserver = mock(Observer.class);
        cTimer.getTickSubject().addObserver(timerObserver);
        cTimer.tick(System.currentTimeMillis());
        verify(timerObserver, never()).update((Observable) any(), any());
    }

    /**
     * Test if reset resetFinishTime() will be called at recreating timer.
     */
    @Test
    public final void testSetFinishTimePersistent() {
        cTimer = new Timer(cTimer.getName(), 60, true);
        cTimer = new Timer(cTimer.getName(), 60);
        cTimer = new Timer(cTimer.getName(), 60, true);
        assertTrue(cTimer.cRunning);
    }

    /**
     * Test if reset resetFinishTime() will not be called if the timer was finished.
     */
    @Test
    public final void testSetFinishTimePersistentFinished() {
        cTimer = new Timer(cTimer.getName(), 60, true);
        cTimer.cPreferences.putLong(cTimer.cName, System.currentTimeMillis() - cTimer.cDuration);
        cTimer = new Timer(cTimer.getName(), 60, true);
        assertFalse(cTimer.cRunning);
    }

    /**
     * Test for the method resetFinishTime().
     */
    @Test
    public final void testResetFinishTime() {
        Timer timer = new Timer("BLABLA", 60, true);
        long time = timer.cPreferences.getLong(timer.cName);
        timer.tick(System.currentTimeMillis() + 1000);
        timer.resetFinishTime();
        assertTrue(timer.cPreferences.getLong(timer.cName) > time);
    }

    /**
     * Test for the method isRunning().
     */
    @Test
    public final void testIsRunning() {
        cTimer.reset();
        assertTrue(cTimer.isRunning());
        cTimer.stop();
        assertFalse(cTimer.isRunning());
    }

    /**
     * Test for the enum INTERVAL.
     */
    @Test
    public final void testTimerEnumINTERVAL() {
        assertEquals(60 * 60, Timer.Global.INTERVAL.getDuration());
    }

    /**
     * Test for the enum STROLL.
     */
    @Test
    public final void testTimerEnumSTROLL() {
        assertEquals(5 * 60, Timer.Global.STROLL.getDuration());
    }

    /**
     * Test for the enum EVENT.
     */
    @Test
    public final void testTimerEnumEVENT() {
        assertEquals(60, Timer.Global.EVENT.getDuration());
    }

    /**
     * Tear down to prepare for the next test.
     */
    @After
    public final void tearDown() {
        Preferences preferences = Gdx.app.getPreferences("TIMER");
        preferences.clear();
        preferences.flush();
    }
}

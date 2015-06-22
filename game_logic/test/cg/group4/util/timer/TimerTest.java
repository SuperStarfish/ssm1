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

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Tests for the timer class.
 */
@RunWith(GdxTestRunner.class)
public class TimerTest {

    /**
     * Seconds in a minute.
     */
    protected final int cTimeInMinute = 60;
    /**
     * Timer that will be tested upon.
     */
    protected Timer cTimer;

    /**
     * Initializes objects for a test.
     */
    @Before
    public final void setUp() {
        cTimer = new Timer("TEST", cTimeInMinute);
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
        cTimer = new Timer(cTimer.getName(), cTimeInMinute, true);
        cTimer = new Timer(cTimer.getName(), cTimeInMinute);
        cTimer = new Timer(cTimer.getName(), cTimeInMinute, true);
        assertFalse(cTimer.cRunning);
    }

    /**
     * Test if reset resetFinishTime() will not be called if the timer was finished.
     */
    @Test
    public final void testSetFinishTimePersistentFinished() {
        cTimer = new Timer(cTimer.getName(), cTimeInMinute, true);
        cTimer.cPreferences.putLong(cTimer.cName, System.currentTimeMillis() - cTimer.cDuration);
        cTimer = new Timer(cTimer.getName(), cTimeInMinute, true);
        assertFalse(cTimer.cRunning);
    }

    /**
     * Test for the method resetFinishTime().
     */
    @Test
    public final void testResetFinishTime() {
        final long tick = 1000;
        Timer timer = new Timer("BLABLA", cTimeInMinute, true);
        long time = timer.cPreferences.getLong(timer.cName);
        timer.tick(System.currentTimeMillis() + tick);
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
        assertEquals(cTimeInMinute * cTimeInMinute, Timer.Global.INTERVAL.getDuration());
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

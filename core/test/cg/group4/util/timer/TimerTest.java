package cg.group4.util.timer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import cg.group4.GdxTestRunner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Tests for the Timer class.
 * 
 * @author CG/Group 4
 */
@RunWith(GdxTestRunner.class)
public class TimerTest {

	/**
	 * Definition of a timer to use in conjunction with the tests.
	 */
	private Timer timer;

	/**
	 * Time timer task mock to use for testing the Timer.
	 */
	private TimerTask timerTask;

	/**
	 * Initializes a test Timer and a mocked TimerTask to use for behavior tests.
	 */
	@Before
	public final void setUp() {
		final int testTime = 5;
		timer = new Timer("TEST", testTime);
		timerTask = mock(TimerTask.class);
	}

	/**
	 * Asserts that a single TimerTask is available to the timer after subscribing.
	 */
	@Test
	public final void testAddTimerTask() {
		timer.subscribe(timerTask);
		assertEquals(1, timer.cTimerTasks.size());
	}

	/**
	 * Verify the name of the timer.
	 */
	@Test
	public final void testGetName() {
		assertEquals("TEST", timer.getName());
	}


	/**
	 * Test the identification of the timer.
	 * A Timer is identified and compared on its name. 
	 * Its duration is not important for the comparison.
	 * This is to assure one, and only one, timer with a specific name exists. 
	 */
	@Test
	public final void testEqualsTrue() {
		final int assertedTimerSeconds = 60;
		assertTrue(timer.equals(new Timer("TEST", assertedTimerSeconds, true)));
	}

	/**
	 * Make sure when a timer is created, it has not a null reference.
	 */
	@Test
	public final void testEqualsNull() {
		assertFalse(timer.equals(null));
	}

	/**
	 * Make sure that timer with name `TEST` is not equal to a non-timer 
	 * (String("BLA")).
	 */
	@Test
	public final void testEqualsNonTimerClass() {
		assertFalse(timer.equals("BLA"));
	}

	/**
	 * Make sure that timer with name `TEST` is not equal to timer with id `WRONG`.
	 * The time of the timer is not important for the comparison.
	 */
	@Test
	public final void testNotEquals() {
		final int testTime = 5;
		assertFalse(timer.equals(new Timer("WRONG", testTime)));
	}

	/**
	 * Verifies the behavior of the TimerTask when subscribing to a Timer.
	 * Verifies that when subscribing to the TimerTask, the onStart event will run.
	 */
	@Test
	public final void testSubscribeOnRunningTimer() {
		timer.subscribe(timerTask);
		verify(timerTask, times(1)).onStart();
	}

	/**
	 * Verifies the behavior of the TimerTask when subscribing to a Timer.
	 * Verifies that after subscribing to the TimerTask, the onTick event will run.
	 * The onTick event runs every X seconds, when the task is not done.
	 * The X is determined by how often per second a timer ticks which is handled by the TimeKeeper.
	 */
	@Test
	public final void testSubscribeOnTimerTick() {
		timer.subscribe(timerTask);
		long timeStamp = System.currentTimeMillis();
		timer.tick(timeStamp);

		final int millisPerSecond = 1000;
		verify(timerTask, times(1)).onTick((int) (timer.cFinishTime - timeStamp) / millisPerSecond);
	}

	/**
	 * Verifies the behavior of the TimerTask when subscribing to a Timer.
	 * Verifies that a timer stops running after the finish time has been reached.
	 */
	@Test
	public final void testOnTickWhenFinished() {
		timer.subscribe(timerTask);
		long timeStamp = System.currentTimeMillis();
		timer.cFinishTime = timeStamp - timer.cDuration;
		timer.tick(timeStamp);
		assertFalse(timer.cRunning);
	}

	/**
	 * Verifies that a timer is not ticking when it is not running.
	 */
	@Test
	public final void testTickWhenNotRunning() {
		timer.stop();
		timer.subscribe(timerTask);
		timer.tick(System.currentTimeMillis());
		verify(timerTask, never()).onTick(Mockito.anyInt());
	}

	/**
	 * Tests that the time of a Timer is persistent (stored in the preferences).
	 */
	@Test
	public final void testSetFinishTimePersistent() {
		final int testTime = 60;
		timer = new Timer("TEST", testTime, true);
		timer = new Timer("TEST", testTime, true);
		assertTrue(timer.cRunning);
	}

	/**
	 * Tests the persistent timer on finishing.
	 */
	@Test 
	public final void testSetFinishTimePersistentFinished() {
		final int testTime = 60;
		timer = new Timer("TEST", testTime, true);
		timer.cPreferences.putLong(timer.cName, System.currentTimeMillis() - timer.cDuration);
		timer = new Timer("TEST", testTime, true);
		assertFalse(timer.cRunning);
	}

	/**
	 * Tests the resetFinishTime by altering the time via the preferences.
	 */
	@Test 
	public final void testResetFinishTime() {
		final int testTime = 60;
		final int sleepTime = 1000;
		//checkstyle.off: local timer
		Timer timer;
		//checkstyle:on: local timer

		try {
			timer = new Timer("BLABLA", testTime, true);
			long time = timer.cPreferences.getLong(timer.cName);
			Thread.sleep(sleepTime);
			timer.resetFinishTime();
			assertTrue(timer.cPreferences.getLong(timer.cName) > time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Verifies that the onStop event is ran by subscribing to a stopped timer.
	 */
	@Test
	public final void testSubscribeOnNotRunningTimer() {
		timer.cPreferences = mock(Preferences.class);
		timer.stop();
		timer.subscribe(timerTask);
		verify(timerTask, times(1)).onStop();
	}

	/**
	 * Tests the duration of the INTERVAL stored in the enum of the timer.
	 * @ignore Not in use, because of play testing purposes (We don't want to wait 1 hour each time).
	 * @see Timer.Global.INTERVAL
	 */
	@Ignore
	public final void testTimerEnumINTERVAL() {
		final int time = 60;
		assertEquals(time * time, Timer.Global.INTERVAL.getDuration());
	}

	/**
	 * Test that a stroll duration is 5 minutes.
	 */
	@Test
	public final void testTimerEnumSTROLL() {
		final int five = 5;
		final int minute = 60;
		assertEquals(five * minute, Timer.Global.STROLL.getDuration());
	}

	/**
	 * Cleans the preferences each test.
	 */
	@After
	public final void tearDown() {
		Preferences preferences = Gdx.app.getPreferences("TIMER");
		preferences.clear();
		preferences.flush();
	}

}

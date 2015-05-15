package cg.group4.util.timer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import cg.group4.GdxTestRunner;

/**
 * Test class for the TimeKeeper.
 * 
 * @author CG/Group 4
 */
@RunWith(GdxTestRunner.class)
public class TimeKeeperTest {
	/**
	 * Creates a timeKeeper object for every test.
	 */
	private final TimeKeeper timeKeeper = TimeKeeper.getInstance();

	/**
	 * 
	 */
	private Timer timer;

	/**
	 * Initializes the set of timers contained by the TimeKeeper.
	 * Creates a timer to use for testing.
	 */
	@Before
	public final void setUp() {
		final int timerTime = 60;
		timeKeeper.cTimers = new HashSet<Timer>();
		timer = new Timer("TEST", timerTime);
	}

	/**
	 * Tests whether after initialization of the global timer, the amount of timers is correct.
	 * The correct amount, is the predefined amount plus one for each global timer initialization.
	 */
	@Test
	public final void testInitGlobalTimers() {
		int count = TimeKeeper.getInstance().cTimers.size();
		timeKeeper.init();
		assertEquals(count + Timer.Global.values().length, TimeKeeper.getInstance().cTimers.size());
	}

	/**
	 * Tests whether an addition of a timer occurs correctly.
	 */
	@Test
	public final void testAddTimer() {
		final int timerTime = 5;
		int size = timeKeeper.cTimers.size();
		timeKeeper.addTimer(new Timer("Test2", timerTime));
		assertEquals(size + 1, timeKeeper.cTimers.size());
	}

	/**
	 * Tests the getter of the TimeKeeper getTimer method on a existing Timer.
	 */
	@Test
	public final void testGetTimer() {
		timeKeeper.addTimer(timer);
		assertTrue(timer.equals(timeKeeper.getTimer(timer.cName)));
	}

	/**
	 * Tests the getter of the TimeKeeper getTimer method on a non existent Timer.
	 */
	@Test
	public final void testGetTimerNull() {
		timeKeeper.addTimer(timer);
		assertNull(timeKeeper.getTimer("TAD"));
	}
}

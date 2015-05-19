package cg.group4.stroll.events;

/**
 * Stroll event used for testing.
 * @author Nick Cleintuar
 */
public class TestStrollEvent extends StrollEvent {
	
	/**
	 * Tag used for debugging.
	 */
	private static final String TAG = TestStrollEvent.class.getSimpleName();

	private static final int REWARD = 10;
	
	/**
	 * Basic variable that makes the event always complete.
	 */
	protected boolean cComplete = true;
	
	/**
	 * Constructor for a test event. Does nothing.
	 */
	public TestStrollEvent() {

	}

}

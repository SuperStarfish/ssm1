package cg.group4.stroll.events;

/**
 * Interface that gets implemented by every event.
 * @author Nick Cleintuar
 */
interface StrollEvent {
	
	/**
	 * Method that gets called every update cycle. 
	 */
	void onUpdate();
	
	/**
	 * Method that gets called when the stroll event is completed.
	 */
	void onComplete();
}

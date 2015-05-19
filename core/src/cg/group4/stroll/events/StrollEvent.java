package cg.group4.stroll.events;

import cg.group4.StandUp;

/**
 * Interface that gets implemented by every event.
 * @author Nick Cleintuar
 * @author Benjamin Los
 * @author Martijn Gribnau
 */
public abstract class StrollEvent {

	private static int REWARD;
	private static Boolean COMPLETE = false;
	
	/**
	 * Method that gets called every update cycle. 
	 */
	public void update(){
		if(COMPLETE){
			onComplete();
		}
	}
	
	/**
	 * Method that gets called when the stroll event is completed.
	 */
	public void onComplete() {
		StandUp.getInstance().getStroll().eventFinished(REWARD);
	}
}

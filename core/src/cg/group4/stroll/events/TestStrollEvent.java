package cg.group4.stroll.events;

import cg.group4.view.MainMenu;
import com.badlogic.gdx.Screen;

/**
 * Stroll event used for testing.
 * @author Nick Cleintuar
 */
public class TestStrollEvent extends StrollEvent {
	
	/**
	 * Constructor for a test event. Does nothing.
	 */
	public TestStrollEvent() {

	}

    @Override
    public int getReward() {
        return 10;
    }

    @Override
    public Screen getScreen() {
        return new MainMenu();
    }

    @Override
    public void update(){
        done();
    }
}

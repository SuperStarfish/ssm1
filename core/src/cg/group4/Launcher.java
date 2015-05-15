package cg.group4;

import cg.group4.util.timer.TimeKeeper;
import cg.group4.view.MainMenu;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * The StandUp class serves as an input point for the LibGDX application.
 * This class handles all the cycles that LibGDX goes through and thus
 * serves as the backbone of the entire application.
 */
public class Launcher extends Game {

	/**
	 * Keeps track of all the timers made.
	 */
	private TimeKeeper timeKeeper;

	/**
	 * Initializes the application.
	 * Does so by creating the TimeKeeper (if non-existent) and setting the
	 * screen to the main menu.
	 */
	@Override
	public final void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		timeKeeper = TimeKeeper.getInstance();
		timeKeeper.init();
		setScreen(new MainMenu());
	}

	/**
	 * Called every frame.
	 * Renders one frame and updates the TimeKeeper accordingly.
	 */
	@Override
	public final void render() {
		timeKeeper.update();
		super.render();
	}
}

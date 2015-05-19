package cg.group4;

import cg.group4.view.MainMenu;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * The Launcher class serves as an input point for the LibGDX application.
 * This class handles all the cycles that LibGDX goes through and thus
 * serves as the backbone of the entire application.
 *
 * The Launcher creates and initializes the StandUp, which serves as the
 * main game logic backbone.
 *
 * @author Benjamin Los
 * @author Martijn Gribnau
 */
public class Launcher extends Game {

	/**
	 * Keeps track of the game mechanics.
	 */
	private StandUp cStandUp;

	/**
	 * Initializes the application.
	 * Does so by creating the TimeKeeper (if non-existent) and setting the
	 * screen to the main menu.
	 */
	@Override
	public final void create() {
		cStandUp = StandUp.getInstance();
        cStandUp.init();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new MainMenu());
	}

	/**
	 * Called every frame.
	 * Renders one frame and updates the TimeKeeper accordingly.
	 */
	@Override
	public final void render() {
        cStandUp.getTimeKeeper().update();
		super.render();
	}
}

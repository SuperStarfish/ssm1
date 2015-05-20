package cg.group4;

import cg.group4.util.camera.WorldRenderer;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.view.MainMenu;
import cg.group4.view.TestScreen;
import cg.group4.view.TestScreen2;
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
	 * Handles viewport and camera. Also draws sprites properly in the game world.
	 */
	private WorldRenderer cWorldRenderer;

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
        cWorldRenderer = new WorldRenderer();
		timeKeeper = TimeKeeper.getInstance();
		timeKeeper.init();
		setScreen(new WorldRenderer());
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

	@Override
	public final void resize(int width, int height){
        super.resize(width, height);
	}

    public WorldRenderer getWorldRenderer(){
        return cWorldRenderer;
    }
}

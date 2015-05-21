package cg.group4;

import cg.group4.game_logic.StandUp;
import cg.group4.view.screen_mechanics.ScreenStore;
import cg.group4.view.screen_mechanics.WorldRenderer;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

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
 * @author Jurgen van Schagen
 */
public class Launcher extends Game {
    /**
     * Used to clear all preferences and other data to start with a 'clean' game.
     */
    public static final boolean CLEAR_SETTINGSS = false;
	/**
	 * Handles viewport and camera. Also draws sprites properly in the game world.
	 */
	private WorldRenderer cWorldRenderer;

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
        if(CLEAR_SETTINGSS){
            Preferences preferences = Gdx.app.getPreferences("TIMER");
            preferences.clear();
            preferences.flush();

        }
		cStandUp = StandUp.getInstance();
        cStandUp.init();
		Gdx.app.setLogLevel(Application.LOG_DEBUG);

        ScreenStore screenStore = StandUp.getInstance().getScreenStore();
		setScreen(screenStore.getWorldRenderer());
        screenStore.setScreen("Home");
	}

	/**
	 * Called every frame.
	 * Renders one frame and updates the TimeKeeper accordingly.
	 */
	@Override
	public final void render() {
		super.render();
		StandUp.getInstance().updateGameMechanics();
		cStandUp.getTimeKeeper().update();
	}

}

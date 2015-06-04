package cg.group4;

import cg.group4.game_logic.StandUp;
import cg.group4.rewards.Collection;
import cg.group4.rewards.collectibles.FishA;
import cg.group4.rewards.collectibles.FishB;
import cg.group4.rewards.collectibles.FishC;
import cg.group4.sensor.AccelerationStatus;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.TimerStore;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * The Launcher class serves as an input point for the LibGDX application.
 * This class handles all the cycles that LibGDX goes through and thus
 * serves as the backbone of the entire application.
 * <p/>
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
    public static final boolean CLEAR_SETTINGS = false;

    /**
     * Keeps track of the game mechanics.
     */
    private StandUp cStandUp;

    /**
     * Keeps track of timers throughout the game.
     */
    private TimeKeeper cTimeKeeper;

    /**
     * Accelerometer status.
     */
    private final AccelerationStatus cAccelerationStatus;

    /**
     * Tunnels the acceleration status through the launcher to the android project.
     * @param accelerationStatus The movement status of the player.
     */
    public Launcher(final AccelerationStatus accelerationStatus) {
        super();
        cAccelerationStatus = accelerationStatus;
    }

    /**
     * Initializes the application.
     * Does so by creating the TimeKeeper (if non-existent) and setting the
     * screen to the main menu.
     */
    @Override
    public final void create() {
        if (CLEAR_SETTINGS) {
            Preferences preferences = Gdx.app.getPreferences("TIMER");
            preferences.clear();
            preferences.flush();
        }
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        cTimeKeeper = TimerStore.getInstance().getTimeKeeper();

        cStandUp = StandUp.getInstance();
        
        //Needs to be moved
        Collection c = new Collection("local");
        c.add(new FishA(780));
        c.add(new FishB(580));
        c.add(new FishC(380));

        ScreenStore cScreenStore = ScreenStore.getInstance();
        setScreen(cScreenStore.getWorldRenderer());
        cScreenStore.init(c);
        cScreenStore.setScreen("Home");
    }

    /**
     * Called every frame.
     * Renders one frame and updates the TimeKeeper accordingly.
     */
    @Override
    public final void render() {
        super.render();
        cStandUp.update();
        cTimeKeeper.update();
    }

}

package cg.group4;

import cg.group4.client.Client;
import cg.group4.client.UserIDResolver;
import cg.group4.collection.Collection;
import cg.group4.collection.RewardGenerator;
import cg.group4.collection.collectibles.Collectible;
import cg.group4.database.datastructures.UserData;
import cg.group4.game_logic.StandUp;
import cg.group4.sensor.AccelerationStatus;
import cg.group4.util.notification.NotificationController;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.Observable;
import java.util.Observer;

/**
 * The Launcher class serves as an input point for the LibGDX application.
 * This class handles all the cycles that LibGDX goes through and thus
 * serves as the backbone of the entire application.
 * <p/>
 * The Launcher creates and initializes the StandUp, which serves as the
 * main game logic backbone.
 */
public class Launcher extends Game {
    /**
     * Used to clear all preferences and other data to start with a 'clean' game.
     */
    public static final boolean CLEAR_SETTINGS = false;

    /**
     * Keeps track of the game mechanics.
     */
    protected StandUp cStandUp;

    /**
     * Keeps track of timers throughout the game.
     */
    protected TimeKeeper cTimeKeeper;

    /**
     * Accelerometer status.
     */
    protected AccelerationStatus cAccelerationStatus;

    /**
     * Gets the device id as uniquer user ID.
     */
    protected final UserIDResolver cIDResolver;

    /**
     * The notification controller to schedule notifications, passed with the constructor of the launcher.
     */
    protected NotificationController cNotificationController;

    /**
     * Tunnels the acceleration status through the launcher to the android project.
     *
     * @param accelerationStatus     The movement status of the player.
     * @param notificationController The notification controller.
     * @param idResolver             The userID resolver for unique device id.
     */
    public Launcher(final AccelerationStatus accelerationStatus, final NotificationController notificationController, final UserIDResolver idResolver) {
        super();
        cAccelerationStatus = accelerationStatus;
        cNotificationController = notificationController;
        cIDResolver = idResolver;
    }

    /**
     * Initializes the application.
     * Does so by creating the TimeKeeper (if non-existent) and setting the
     * screen to the main menu.
     */
    @Override
    public final void create() {
        debugSetup();

        cTimeKeeper = TimerStore.getInstance().getTimeKeeper();

        cStandUp = StandUp.getInstance();

        //Needs to be moved
        Collection c = new Collection("local");
        RewardGenerator gen = new RewardGenerator();
        for (int i = 0; i < 10; i++) {
            Collectible ca = gen.generateCollectible(1);
        	c.add(ca);
        }

        Client.getInstance().setUserIDResolver(cIDResolver);
        Client.getInstance().connectToServer();

        ScreenStore cScreenStore = ScreenStore.getInstance();
        setScreen(cScreenStore.getWorldRenderer());
        cScreenStore.init(c);
        cScreenStore.setScreen("Home");

        notificationInitialization();
    }

    /**
     * Sets up the game with the specified debug levels.
     */
    private void debugSetup() {
        if (CLEAR_SETTINGS) {
            Preferences preferences = Gdx.app.getPreferences("TIMER");
            preferences.clear();
            preferences.flush();
        }
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
    }

    /**
     * Initializes the game to send notifications.
     */
    private void notificationInitialization() {
        final Timer intervalTimer = TimerStore.getInstance().getTimer(Timer.Global.INTERVAL.name());

        intervalTimer.getStartSubject().addObserver(new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                cNotificationController.scheduleNotification(intervalTimer.getFinishTime());
            }
        });
        if (intervalTimer.isRunning()) {
            cNotificationController.scheduleNotification(intervalTimer.getFinishTime());
        }
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

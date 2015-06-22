package cg.group4;

import cg.group4.client.Client;
import cg.group4.client.UserIDResolver;
import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.RewardGenerator;
import cg.group4.game_logic.StandUp;
import cg.group4.server.LocalStorageResolver;
import cg.group4.server.Server;
import cg.group4.util.notification.NotificationController;
import cg.group4.util.orientation.OrientationReader;
import cg.group4.util.sensor.AccelerationStatus;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import cg.group4.view.screen.HomeScreen;
import cg.group4.view.screen_mechanics.AssetsLoadingHandler;
import cg.group4.view.screen_mechanics.LoadingScreen;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.*;

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
public class Launcher extends Game implements AssetsLoadingHandler {
    /**
     * Used to clear all preferences and other data to start with a 'clean' game.
     */
    public static final boolean CLEAR_SETTINGS = false;
    /**
     * Gets the device id as uniquer user ID.
     */
    protected final UserIDResolver cIDResolver;
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
     * The notification controller to schedule notifications, passed with the constructor of the launcher.
     */
    protected NotificationController cNotificationController;
    /**
     * Used to determine where the local server lives.
     */
    protected LocalStorageResolver cLocalStorageResolver;

    /**
     * Reads the device's current orientation.
     */
    protected OrientationReader cOrientationReader;

    /**
     * Tunnels the acceleration status through the launcher to the android project.
     *
     * @param accelerationStatus     The movement status of the player.
     * @param notificationController The notification controller.
     * @param idResolver             The userID resolver for unique device id.
     * @param localStorageResolver   The location where to find the local server.
     * @param orientationReader      The orientation the game is currently in.
     */
    public Launcher(final AccelerationStatus accelerationStatus,
                    final NotificationController notificationController,
                    final UserIDResolver idResolver,
                    final LocalStorageResolver localStorageResolver,
                    final OrientationReader orientationReader) {
        super();
        cAccelerationStatus = accelerationStatus;
        cNotificationController = notificationController;
        cIDResolver = idResolver;
        cLocalStorageResolver = localStorageResolver;
        cOrientationReader = orientationReader;
    }

    /**
     * Initializes the application.
     * Does so by creating the TimeKeeper (if non-existent) and setting the screen to the main menu.
     */
    @Override
    public final void create() {
        debugSetup();
        initClient();
        Gdx.input.setInputProcessor(new InputMultiplexer());

        setScreen(new LoadingScreen(this));

        RewardGenerator gen = new RewardGenerator(Client.getInstance().getUserID());
        Collection collection = new Collection("");
        for (int i = 0; i < 20; i++) {
            collection.add(gen.generateCollectible(1));
        }
        StandUp.getInstance().getPlayer().getCollection().addAll(collection);
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
     * Initializes the client.
     */
    protected void initClient() {
        Server server = new Server(cLocalStorageResolver);
        server.start();
        Client.getInstance().setUserIDResolver(cIDResolver);
        Client.getInstance().connectToLocalServer(server.getSocketPort());
        Client.getInstance().connectToRemoteServer();
    }

    /**
     * Once the Assets are done loading, this method is called to properly initialize the game.
     */
    public void assetsDone() {
        cTimeKeeper = TimerStore.getInstance().getTimeKeeper();
        cStandUp = StandUp.getInstance();
        cStandUp.setAccelerationStatus(cAccelerationStatus);
        cStandUp.setOrientationReader(cOrientationReader);

        initScreens();

        notificationInitialization();
    }

    /**
     * Initiates the screens.
     */
    protected void initScreens() {
        ScreenStore cScreenStore = ScreenStore.getInstance();
        setScreen(cScreenStore.getWorldRenderer());
        cScreenStore.addScreen("Home", new HomeScreen());
        cScreenStore.setScreen("Home");
    }

    /**
     * Initializes the game to send notifications.
     */
    protected void notificationInitialization() {
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
        if (cStandUp != null && cTimeKeeper != null) {
            cTimeKeeper.update();
            cStandUp.update();
        }
        for (Runnable toRunBeforeNextCycle : Client.getInstance().getPostRunnables()) {
            Gdx.app.postRunnable(toRunBeforeNextCycle);
        }
        Client.getInstance().resetPostRunnables();
    }
}

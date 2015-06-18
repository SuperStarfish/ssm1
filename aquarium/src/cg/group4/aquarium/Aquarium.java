package cg.group4.aquarium;

import cg.group4.client.Client;
import cg.group4.data_structures.subscribe.Subject;
import cg.group4.view.AquariumScreen;
import cg.group4.view.StartScreen;
import cg.group4.view.screen_mechanics.AssetsLoadingHandler;
import cg.group4.view.screen_mechanics.LoadingScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * The purpose of the Launcher is to launch the collection application.
 * This Launcher is a separate launcher from the Launcher in the core project.
 * This is because it does not require several pieces such as game logic.
 */
public class Aquarium extends Game implements AssetsLoadingHandler {

    /**
     * Instance of aquarium.
     */
    protected static volatile Aquarium instance;

    /**
     * Connects the client with the server.
     */
    protected Connector cConnector;

    /**
     * Loads the configuration for the server.
     */
    protected Configuration cAquariumConfig;

    /**
     * Screen which displays the aquarium.
     */
    protected AquariumScreen cAquariumScreen;

    /**
     * Screen which displays a textfield where the user can fill in the group cId to show the aligned aquarium.
     */
    protected StartScreen cStartScreen;

    /**
     * Private aquarium constructor.
     */
    private Aquarium() {
    }

    /**
     * Returns the singleton instance of the aquarium.
     * @return Aquarium singleton.
     */
    public static Aquarium getInstance() {
        if (instance == null ) {
            synchronized (Aquarium.class) {
                if (instance == null) {
                    instance = new Aquarium();
                }
            }
        }
        return instance;
    }

    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
    }


    @Override
    public void render() {
        super.render();

        for (Runnable toRunBeforeNextCycle : Client.getRemoteInstance().getPostRunnables()) {
            Gdx.app.postRunnable(toRunBeforeNextCycle);
        }
        Client.getRemoteInstance().resetPostRunnables();
    }

    /**
     * Called by the loading screen when the asset loading is finished.
     */
    public void assetsDone() {
        cAquariumConfig = new Configuration();

        setScreen(new StartScreen());
    }

    /**
     * Initializes the aquarium (screen and server connection) with the given group cId.
     * @param groupNumber group cId
     */
    public void initAquarium(String groupNumber) {
        cConnector = new Connector(groupNumber, cAquariumConfig);
        cAquariumScreen = new AquariumScreen();
        cConnector.getCollectionSubject().addObserver(cAquariumScreen.getCollectionObserver());
        setScreen(cAquariumScreen);
    }


}

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
     * Screen which displays a textfield where the user can fill in the group id to show the aligned aquarium.
     */
    protected StartScreen cStartScreen;

    /**
     *
     */
    protected Subject cGroupIdSubject;


    private Aquarium() {
    }

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

        for(Runnable toRunBeforeNextCycle : Client.getRemoteInstance().getPostRunnables()) {
            Gdx.app.postRunnable(toRunBeforeNextCycle);
        }
        Client.getRemoteInstance().resetPostRunnables();
    }

    public void assetsDone() {
        cAquariumConfig = new Configuration();

        cStartScreen = new StartScreen();
        setScreen(cStartScreen);
    }

    /**
     * Initializes the aquarium (screen and server connection) with the given group id.
     * @param groupNumber group id
     */
    public void initAquarium(String groupNumber) {
        cConnector = new Connector(groupNumber);
        cAquariumScreen = new AquariumScreen();
        cConnector.getCollectionSubject().addObserver(cAquariumScreen.getCollectionObserver());
        setScreen(cAquariumScreen);
    }
}

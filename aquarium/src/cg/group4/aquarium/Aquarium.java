package cg.group4.aquarium;

import cg.group4.client.Client;
import cg.group4.view.aquarium.AquariumScreen;
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
     * Screen which displays the aquarium.
     */
    protected AquariumScreen cAquariumScreen;

    /**
     * Private aquarium constructor.
     */
    private Aquarium() {
    }

    /**
     * Returns the singleton instance of the aquarium.
     *
     * @return Aquarium singleton.
     */
    public static Aquarium getInstance() {
        if (instance == null) {
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

        for (Runnable toRunBeforeNextCycle : Client.getInstance().getPostRunnables()) {
            Gdx.app.postRunnable(toRunBeforeNextCycle);
        }
        Client.getInstance().resetPostRunnables();
    }

    /**
     * Called by the loading screen when the asset loading is finished.
     */
    public void assetsDone() {
        cConnector = new Connector();
        cAquariumScreen = new AquariumScreen();
        cConnector.getMembersSubject().addObserver(cAquariumScreen.getMembersObserver());
        cConnector.getGroupDataSubject().addObserver(cAquariumScreen.getGroupDataObserver());
        cConnector.getCollectionSubject().addObserver(cAquariumScreen.getCollectionObserver());

        setScreen(cAquariumScreen);
    }

    /**
     * Initializes the aquarium (screen and server connection) with the given group cId.
     *
     * @param groupNumber group cId
     */
    public void setGroupId(String groupNumber) {
        cConnector.setGroupId(groupNumber);
    }


    public AquariumScreen getAquariumScreen() {
        return cAquariumScreen;
    }
}

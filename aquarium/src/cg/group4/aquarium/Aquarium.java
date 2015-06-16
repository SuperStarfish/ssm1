package cg.group4.aquarium;

import cg.group4.client.Client;
import cg.group4.view.AquariumScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * The purpose of the Launcher is to launch the collection application.
 * This Launcher is a separate launcher from the Launcher in the core project.
 * This is because it does not require several pieces such as game logic.
 */
public class Aquarium extends Game {
    protected Connector cConnector;
    protected Configuration cAquariumConfig;
    protected AquariumScreen cAquariumScreen;

    public void init() {
        // todo
        cAquariumConfig = new Configuration();
        cConnector = new Connector("7");
        cConnector.fetchCollectionFromServer();

        System.out.println("Connecting to " + cAquariumConfig.getHost() + " : " + cAquariumConfig.getPort());
    }

    @Override
    public void create() {
        init();
        cAquariumScreen = new AquariumScreen(cConnector.getCollection());
        setScreen(cAquariumScreen);
    }


    @Override
    public void render() {
        for(Runnable toRunBeforeNextCycle : Client.getRemoteInstance().getPostRunnables()) {
            Gdx.app.postRunnable(toRunBeforeNextCycle);
        }
        Client.getRemoteInstance().resetPostRunnables();
    }
}

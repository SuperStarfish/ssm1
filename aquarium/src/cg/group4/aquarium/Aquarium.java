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

    @Override
    public void create() {
        cAquariumConfig = new Configuration();

        // TODO: make dynamic.
        cConnector = new Connector("7");

        cAquariumScreen = new AquariumScreen();

        cConnector.getSubject().addObserver(cAquariumScreen);

        setScreen(cAquariumScreen);
    }


    @Override
    public void render() {
        super.render();

        for(Runnable toRunBeforeNextCycle : Client.getRemoteInstance().getPostRunnables()) {
            Gdx.app.postRunnable(toRunBeforeNextCycle);
        }
        Client.getRemoteInstance().resetPostRunnables();
    }
}

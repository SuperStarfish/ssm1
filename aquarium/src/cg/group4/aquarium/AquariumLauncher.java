package cg.group4.aquarium;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * The AquariumLauncher is the starting point to launch a fish-tank version of the game.
 * This class is mostly used for displaying a group collection aquarium on a display.
 */
public class AquariumLauncher {

    /**
     * Starts the application.
     * Uses a borderless fullscreen window.
     *
     * @param arg Arguments of the application.
     */
    public static void main(final String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
        config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
        config.fullscreen = true;

        new LwjglApplication(Aquarium.getInstance(), config);
    }


}
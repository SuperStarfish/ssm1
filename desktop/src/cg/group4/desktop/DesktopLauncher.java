package cg.group4.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import cg.group4.Launcher;

/**
 * The DesktopLauncher is the starting point to launch a Desktop version of the game.
 * This class is mostly used for debugging purposes, since the actual game will run
 * on Android.
 */
public class DesktopLauncher {
    public static final int DESKTOP_WIDTH = 1280;
    public static final int DESKTOP_HEIGHT = 720;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = DESKTOP_WIDTH;
        config.height = DESKTOP_HEIGHT;
		new LwjglApplication(new Launcher(), config);
	}
}

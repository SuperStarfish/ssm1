package cg.group4.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import cg.group4.StandUp;

/**
 * The DesktopLauncher is the starting point to launch a Desktop version of the game.
 * This class is mostly used for debugging purposes, since the actual game will run
 * on Android.
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new StandUp(), config);
	}
}

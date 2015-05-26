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
    /**
     * Uses the Aspect enum to determine what height and width to use for testing the screen on desktop.
     */
    public static final Aspect aspect = Aspect.RATIO9_16;

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = aspect.getWidth();
        config.height = aspect.getHeight();
		new LwjglApplication(new Launcher(), config);
    }

    /**
     * These enums can be used to see how the game looks on different aspect ratios.
     */
    public enum Aspect{
        RATIO16_9(1280, 720),
        RATIO5_3(1200, 720),
        RATIO16_10(1152, 720),
        RATIO3_2(1080, 720),
        RATIO4_3(960, 720),
        RATIO3_4(720, 960),
        RATIO9_16(540, 960);
        protected int cWidth, cHeight;
        Aspect(int width, int height){
            cWidth = width;
            cHeight = height;
        }

        public int getWidth(){
            return cWidth;
        }

        public int getHeight(){
            return cHeight;
        }

        public float getRatio() { return cWidth / (float)cHeight; }
    }
}

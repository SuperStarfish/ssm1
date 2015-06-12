package cg.group4.desktop;

import cg.group4.Launcher;
import cg.group4.client.DesktopIDResolver;
import cg.group4.server.DesktopStorageResolver;
import cg.group4.util.notification.DesktopNotificationController;
import cg.group4.util.sensor.DesktopAccelerationStatus;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * The DesktopLauncher is the starting point to launch a Desktop version of the game.
 * This class is mostly used for debugging purposes, since the actual game will run
 * on Android.
 */
public class DesktopLauncher {
    /**
     * Uses the Aspect enum to determine what height and width to use for testing the screen on desktop.
     */
    public static final Aspect ASPECT = Aspect.RATIO9_16;
    /**
     * Starts the application.
     *
     * @param arg Arguments of the application.
     */
    public static void main(final String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = ASPECT.getWidth();
        config.height = ASPECT.getHeight();

        new LwjglApplication(new Launcher(
                new DesktopAccelerationStatus(),
                new DesktopNotificationController(),
                new DesktopIDResolver(),
                new DesktopStorageResolver()),
                config);

    }

    /**
     * These enums can be used to see how the game looks on different aspect ratios.
     */
    public enum Aspect {
        /**
         * 16:9 ratio.
         */
        RATIO16_9(1280, 720),

        /**
         * 5:3 ratio.
         */
        RATIO5_3(1200, 720),

        /**
         * 16:10 ratio.
         */
        RATIO16_10(1152, 720),

        /**
         * 3:2 ratio.
         */
        RATIO3_2(1080, 720),

        /**
         * 4:3 ratio.
         */
        RATIO4_3(960, 720),

        /**
         * 3:4 ratio.
         */
        RATIO3_4(720, 960),

        /**
         * 9:16 ratio.
         */
        RATIO9_16(540, 960);

        /**
         * Fields containing the width and height.
         */
        protected int cWidth, cHeight;

        /**
         * @param width  Width in pixels
         * @param height Height in pixels
         */
        Aspect(final int width, final int height) {
            cWidth = width;
            cHeight = height;
        }

        /**
         * Returns the width of the ratio.
         *
         * @return Width in pixels.
         */
        public int getWidth() {
            return cWidth;
        }

        /**
         * Returns the width of the ratio.
         *
         * @return Width in pixels.
         */
        public int getHeight() {
            return cHeight;
        }

        /**
         * Returns the ratio as float.
         *
         * @return Float equal to width / height of the ratio.
         */
        public float getRatio() {
            return cWidth / (float) cHeight;
        }
    }
}

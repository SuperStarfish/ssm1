package cg.group4.aquarium;

import cg.group4.Launcher;
import cg.group4.client.AquariumIDResolver;
import cg.group4.util.notification.AquariumNotificationController;
import cg.group4.util.sensor.AquariumAccelerationStatus;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * The AquariumLauncher is the starting point to launch a fish-tank version of the game.
 * This class is mostly used for displaying a group collection aquarium on a display.
 */
public class AquariumLauncher {

    /**
     * Uses the Aspect enum to determine what height and width to use in the aquarium.
     */
    public static final Aspect ASPECT = Aspect.RATIO16_9;

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
                new AquariumAccelerationStatus(),
                new AquariumNotificationController(),
                new AquariumIDResolver()),
                config);

    }

    /**
     * These enums can be used to see how the game looks on different aspect ratios.
     */
    public enum Aspect {
        /**
         * 16:9 ratio.
         */
        RATIO16_9(1920, 1080),

        /**
         * 16:10 ratio.
         */
        RATIO16_10(1152, 720);


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

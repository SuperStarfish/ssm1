package cg.group4.util.orientation;

/**
 * Orientation reader for the desktop.
 */
public class DesktopOrientationReader implements OrientationReader {

    /**
     * Since the events dependent on the orientation do not work on the desktop
     * this method simply returns a Portrait orientation.
     */
    @Override
    public Orientation getOrientation() {
        return new Portrait();
    }

}

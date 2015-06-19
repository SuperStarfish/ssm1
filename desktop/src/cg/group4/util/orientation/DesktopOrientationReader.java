package cg.group4.util.orientation;

public class DesktopOrientationReader implements OrientationReader {

    /**
     * Since the events dependant on the orientation do not work on the desktop
     * this method return ORIENTATION.UNDEFINED
     */
    @Override
    public Orientation getOrientation() {
        return new Portrait();
    }

}

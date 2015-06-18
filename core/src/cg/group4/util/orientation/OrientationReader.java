package cg.group4.util.orientation;

/**
 * Interface used to define the orientation for the device.
 */
public interface OrientationReader {

    /**
     * Current device orientation.
     * @return Orientation of the device.
     */
	Orientation getOrientation();

}

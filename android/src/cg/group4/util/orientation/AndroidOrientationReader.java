package cg.group4.util.orientation;

import android.content.Context;

/**
 * Orientation reader for android devices.
 */
public class AndroidOrientationReader implements OrientationReader {

    /**
     * Current context of the application.
     */
    protected Context cContext;

    /**
     * Constructs a new android orientation reader object.
     *
     * @param context current context of the application
     */
    public AndroidOrientationReader(Context context) {
        cContext = context;
    }

    @Override
    public Orientation getOrientation() {
        int orientation = cContext.getResources().getConfiguration().orientation;
        Orientation result = null;
        if (orientation == 1) {
            result = new Portrait();
        } else if (orientation == 2) {
            result = new Landscape();
        }
        return result;
    }
}

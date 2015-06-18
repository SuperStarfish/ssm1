package cg.group4.util.orientation;

import android.content.Context;

public class AndroidOrientationReader implements OrientationReader {

    protected Context cContext;

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

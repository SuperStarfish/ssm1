package cg.group4.util.orientation;

/**
 * Object that defines the accelerometer axes for landscape orientation.
 */
public class Landscape implements Orientation {
	
	/**
	 * Number corresponding to the landscape orientation.
	 */
    protected static final int ORIENTATION_NUMBER = 2;
    
    /**
     * Integers used for the transformation between axes in portrait mode and axes in landscape mode.
     */
    protected final int cMoveLeft = 2, cMoveRight = 3, cMoveDown = 1, cMoveUp = 0;

    @Override
    public int transformOperation(final int operationNr) {
        int result;
        switch (operationNr) {
            case 0:
                result = cMoveLeft;
                break;
            case 1:
                result = cMoveRight;
                break;
            case 2:
                result = cMoveDown;
                break;
            case 3:
                result = cMoveUp;
                break;
            default:
                result = operationNr;
                break;
        }
        return result;
    }

    @Override
    public int getOrientationNumber() {
        return ORIENTATION_NUMBER;
    }

    @Override
    public int getTextIndex(final int operationNr) {
        return operationNr;
    }
}

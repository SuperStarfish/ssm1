package cg.group4.util.orientation;

/**
 * Methods that need to be implemented for every orientation.
 */
public interface Orientation {

    /**
     * Transforms a operation number in landscape mode to portrait mode and vice versa.
     *
     * @param operationNr the number to be transformed.
     * @return the transformed number.
     */
    int transformOperation(final int operationNr);

    /**
     * Returns the orientation number corresponding to the current orientation.
     *
     * @return orientation number corresponding to the current orientation.
     */
    int getOrientationNumber();

    /**
     * Returns the index of the text to be displayed for the given operation number.
     *
     * @param operationNr current operation number that needs a corresponding textnumber.
     * @return index corresponding to the text needed for the given operation number in the current orientation.
     */
    int getTextIndex(final int operationNr);
}

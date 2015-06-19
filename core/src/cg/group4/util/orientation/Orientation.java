package cg.group4.util.orientation;

public interface Orientation {

    int transformOperation(final int operationNr);

    int getOrientationNumber();

    int getTextIndex(final int operationNr);
}

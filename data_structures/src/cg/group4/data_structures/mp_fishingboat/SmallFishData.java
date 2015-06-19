package cg.group4.data_structures.mp_fishingboat;

import java.io.Serializable;
import java.util.Random;

public class SmallFishData implements Serializable {

    /**
     * Width of the smallfish. Scales with different resolutions to a maximum resolution of 1440p.
     */
    protected final double cFishSizeX = 64 / 1440d;

    /**
     * Height of the smallfish. Scales with different resolutions to a maximum resolution of 2560p..
     */
    protected final double cFishSizeY = 64 / 2560d;
    /**
     * Speed of the smallfish.
     */
    protected final float cSpeed = 0.002f;
    /**
     * cPosition: current position of the smallfish.
     * cDestination: coordinates of the destination of the smallfish.
     */
    protected Coordinate cPosition, cDestination;

    /**
     * Constructs a new SmallFishData object.
     */
    public SmallFishData() {
        cPosition = generatePosition();
        cDestination = generatePosition();
    }

    /**
     * Generates a new random position on the screen.
     *
     * @return Coordinate object representing a position on the screen.
     */
    public Coordinate generatePosition() {
        Random rng = new Random();
        return new Coordinate(rng.nextFloat(), rng.nextFloat());
    }

    /**
     * Returns the position of the smallfish.
     *
     * @return Coordinate object representing the position of the smallfish on the screen.
     */
    public Coordinate getPosition() {
        return cPosition;
    }

    /**
     * Sets the position of the smallfish to the given coordinate.
     *
     * @param position new position for the smallfish.
     */
    public void setPosition(Coordinate position) {
        cPosition = position;
    }

    /**
     * Returns the destination of the smallfish.
     *
     * @return Coordinate object representing the current destination of the smallfish.
     */
    public Coordinate getDestination() {
        return cDestination;
    }

    /**
     * Sets the destination of the smallfish to the given coordinate.
     *
     * @param destination new destination
     */
    public void setDestination(Coordinate destination) {
        cDestination = destination;
    }

    /**
     * Checks if the destination of the smallfish has been reached, in other words,
     * checks if the current location is the same as the destination.
     *
     * @return boolean representing whether the destination has been reached.
     */
    public boolean destinationReached() {
        boolean xCheck = cDestination.getX() <= cPosition.getX() + cFishSizeX
                && cDestination.getX() >= cPosition.getX();

        boolean yCheck = cDestination.getY() <= cPosition.getY() + cFishSizeY
                && cDestination.getY() >= cPosition.getY();
        return xCheck && yCheck;
    }

    /**
     * Moves the smallfish closer to its destination.
     */
    public void move() {
        float diffX = cDestination.getX() - getCenterX();
        float diffY = cDestination.getY() - getCenterY();

        float newX = cPosition.getX() + cSpeed * Math.signum(diffX);
        float newY = cPosition.getY() + cSpeed * Math.signum(diffY);

        cPosition.setX(newX);
        cPosition.setY(newY);
    }

    /**
     * Returns the X location of the center point of the smallfish. (With regards to its sprite).
     *
     * @return float between [0-1] where 1 is the screen width.
     */
    public float getCenterX() {
        return cPosition.getX() + (float) cFishSizeX / 2;
    }

    /**
     * Returns the Y location of the center point of the smallfish. (With regards to its sprite).
     *
     * @return float between [0-1] where 1 is the screen height.
     */
    public float getCenterY() {
        return cPosition.getY() + (float) cFishSizeY / 2;
    }

    /**
     * Checks whether the given boundaries overlap with the smallfish.
     * This only works for rectangles!
     *
     * @param xMin Left most position of the rectangle
     * @param xMax Right most position of the rectangle
     * @param yMin Bottom most position of the rectangle
     * @param yMax Top most position of the rectangle
     * @return boolean whether or not the rectangle and the smallfish overlap.
     */
    public boolean intersects(double xMin, double xMax, double yMin, double yMax) {
        double fishXMin = cPosition.getX();
        double fishYMin = cPosition.getY();
        double fishXMax = fishXMin + cFishSizeX;
        double fishYMax = fishYMin + cFishSizeY;

        double xOverlap = Math.max(0, Math.min(xMax, fishXMax) - Math.max(xMin, fishXMin));
        double yOverlap = Math.max(0, Math.min(yMax, fishYMax) - Math.max(yMin, fishYMin));

        return (xOverlap * yOverlap) > 0d;
    }

}

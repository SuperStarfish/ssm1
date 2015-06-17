package cg.group4.data_structures.mp_fishingboat;

import java.io.Serializable;
import java.util.Random;

public class SmallFishData implements Serializable {

    protected Coordinate cPosition, cDestination;

    protected float cSpeed = 0.002f;
    protected final float fishSize = 32 / 1440f;

    public SmallFishData() {
        cPosition = generatePosition();
        cDestination = generatePosition();
    }

    public Coordinate getPosition() {
        return cPosition;
    }

    public Coordinate generatePosition() {
        Random rng = new Random();
        return new Coordinate(rng.nextFloat(), rng.nextFloat());
    }

    public boolean destinationReached() {
        boolean xCheck = cDestination.getX() <= cPosition.getX() + fishSize && cDestination.getX() >= cPosition.getX();
        boolean yCheck = cDestination.getY() <= cPosition.getY() + fishSize && cDestination.getY() >= cPosition.getY();
        return xCheck && yCheck;
    }

    public void move() {
        float diffX = cDestination.getX() - getCenterX();
        float diffY = cDestination.getY() - getCenterY();

        float newX = cPosition.getX() + cSpeed * Math.signum(diffX);
        float newY = cPosition.getY() + cSpeed * Math.signum(diffY);

        cPosition.setX(newX);
        cPosition.setY(newY);
    }

    public void setDestination(Coordinate destination) {
        cDestination = destination;
    }

    public float getCenterX() {
        return cPosition.getX() + fishSize / 2;
    }

    public float getCenterY() {
        return cPosition.getY() + fishSize / 2;
    }

}

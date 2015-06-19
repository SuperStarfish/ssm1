package cg.group4.data_structures.mp_fishingboat;

import java.io.Serializable;

public class BoatData implements Serializable {
    public Coordinate cLocation;
    public double cRotation;

    public BoatData(Coordinate location, double rotation) {
        cLocation = location;
        cRotation = rotation;
    }

    public Coordinate getcLocation() {
        return cLocation;
    }

    public void setcLocation(Coordinate cLocation) {
        this.cLocation = cLocation;
    }

    public double getcRotation() {
        return cRotation;
    }

    public void setcRotation(double cRotation) {
        this.cRotation = cRotation;
    }
}

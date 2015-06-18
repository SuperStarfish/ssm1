package cg.group4.data_structures.mp_fishingboat;

import java.io.Serializable;

public class SmallFishDestination implements Serializable{
    protected int cId;
    protected Coordinate cNewDestination;

    public SmallFishDestination(int id, Coordinate destination) {
        cId = id;
        cNewDestination = destination;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public Coordinate getcNewDestination() {
        return cNewDestination;
    }

    public void setcNewDestination(Coordinate cNewDestination) {
        this.cNewDestination = cNewDestination;
    }
}

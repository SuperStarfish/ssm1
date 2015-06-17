package cg.group4.data_structures.mp_fishingboat;

import java.io.Serializable;
import java.util.HashMap;

public class FishingBoatData implements Serializable{
    Coordinate cBoatCoordinate;
    double cCraneRotation;
    HashMap<Integer, SmallFishData> cSmallFishCoordinates;

    protected static final float developSize = 1440;
    protected static final float boatBoundary = 256 / developSize;

    public static float getDevelopSize() {
        return developSize;
    }

    public static float getBoatSize() {
        return boatBoundary;
    }

    public FishingBoatData() {
        cBoatCoordinate = new Coordinate(0.5f, 0.5f);
        cCraneRotation = 0f;
        cSmallFishCoordinates = spawnFish();
    }

    public Coordinate getcBoatCoordinate() {
        return cBoatCoordinate;
    }

    public void setcBoatCoordinate(Coordinate cBoatCoordinate) {
        this.cBoatCoordinate = cBoatCoordinate;
    }

    public double getcCraneRotation() {
        return cCraneRotation;
    }

    public void setcCraneRotation(double cCraneRotation) {
        this.cCraneRotation = cCraneRotation;
    }

    public HashMap<Integer, SmallFishData> getcSmallFishCoordinates() {
        return cSmallFishCoordinates;
    }

    public void setcSmallFishCoordinates(HashMap<Integer, SmallFishData> cSmallFishCoordinates) {
        this.cSmallFishCoordinates = cSmallFishCoordinates;
    }

    protected HashMap<Integer, SmallFishData> spawnFish() {
        HashMap<Integer, SmallFishData> fishes = new HashMap<>();
        for(int i = 0; i < 10; i++) {
            fishes.put(i, new SmallFishData());
        }
        return fishes;
    }
}
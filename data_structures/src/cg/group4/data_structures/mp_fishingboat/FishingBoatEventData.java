package cg.group4.data_structures.mp_fishingboat;

import java.io.Serializable;
import java.util.HashMap;

public class FishingBoatEventData implements Serializable {
    BoatData cBoatData;
    double cCraneRotation;
    HashMap<Integer, SmallFishData> cSmallFishCoordinates;

    public FishingBoatEventData() {
        cBoatData = new BoatData(new Coordinate(0.5f, 0.5f), 0);
        cCraneRotation = 0f;
        cSmallFishCoordinates = spawnFish();
    }

    protected HashMap<Integer, SmallFishData> spawnFish() {
        HashMap<Integer, SmallFishData> fishes = new HashMap<Integer, SmallFishData>();
        for (int i = 0; i < 10; i++) {
            fishes.put(i, new SmallFishData());
        }
        return fishes;
    }

    public BoatData getcBoatData() {
        return cBoatData;
    }

    public void setcBoatData(BoatData cBoatData) {
        this.cBoatData = cBoatData;
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
}
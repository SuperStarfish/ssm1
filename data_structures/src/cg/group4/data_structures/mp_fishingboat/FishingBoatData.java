package cg.group4.data_structures.mp_fishingboat;

import java.util.HashMap;

public class FishingBoatData {
    Coordinate cBoatCoordinate, cCraneHitbox;
    Rotation cCraneRotation;
    HashMap<Integer, Coordinate> cSmallFishCoordinates;

    public FishingBoatData() {
        cBoatCoordinate = new Coordinate(0.5f, 0.5f);
        cCraneRotation = new Rotation(0);
    }
}
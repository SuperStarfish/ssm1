package cg.group4.game_logic.stroll.events.multiplayer;

import java.util.Observable;

import cg.group4.data_structures.mp_fishingboat.Coordinate;
import cg.group4.data_structures.mp_fishingboat.FishingBoatData;

import cg.group4.data_structures.mp_fishingboat.SmallFishData;
import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.util.sensor.Accelerometer;
import com.badlogic.gdx.math.Vector3;

public class CraneFishing extends StrollEvent{

    protected FishingBoatData fishingBoatData;

    protected Accelerometer cAccelmeter;
    protected float cSpeed = 0.0025f;
    protected final float developSize = 1440;
    protected final float boatBoundary = 256 / developSize;

    public CraneFishing() {
        super();
        cAccelmeter = new Accelerometer(StandUp.getInstance().getSensorReader());
        cAccelmeter.filterGravity(false);
        cAccelmeter.setNoiseThreshold(0.5f);
        cAccelmeter.setFilterPerAxis(true);

        fishingBoatData = new FishingBoatData();
    }

    @Override
    public int getReward() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected void clearEvent() {
        // TODO Auto-generated method stub

    }

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(Observable o, Object arg) {
        Vector3 vector = cAccelmeter.update();
        if(vector.x != 0 || vector.y != 0 ) {
            double angle = Math.atan2(-vector.y, -vector.x);
            fishingBoatData.setcCraneRotation(angle);
            moveBoat(vector);
            moveFish();
        }

        cDataSubject.update(fishingBoatData);
    }

    protected void moveFish() {
        for(SmallFishData fish : fishingBoatData.getcSmallFishCoordinates().values()) {
            fish.move();
        }
    }

	protected void moveBoat(Vector3 vector) {
		float totalForce = Math.abs(vector.x) + Math.abs(vector.y);
        Coordinate boatCoordinate = fishingBoatData.getcBoatCoordinate();
		float oldX = boatCoordinate.getX();
		float oldY = boatCoordinate.getY();
		if(totalForce != 0) {
            float newX = oldX - cSpeed * (vector.x / totalForce);
            float newY = oldY - cSpeed * (vector.y / totalForce);
            if(newX >= 0 && newX <= 1 - boatBoundary) {
                boatCoordinate.setX(newX);
            }
            if(newY >= 0 && newY <= 1 - boatBoundary) {
                boatCoordinate.setY(newY);
            }
		}
	}

}

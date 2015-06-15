package cg.group4.game_logic.stroll.events.multiplayer;

import java.util.Observable;

import cg.group4.data_structures.mp_fishingboat.FishingBoatData;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.util.sensor.Accelerometer;

public class CraneFishing extends StrollEvent {

    protected FishingBoatData fishingBoatData;

	protected Accelerometer cAccelmeter;
	protected int cSpeed = 4;

	public CraneFishing() {
		super();
		cAccelmeter = new Accelerometer(StandUp.getInstance().getSensorReader());
		cAccelmeter.filterGravity(false);
		cAccelmeter.setNoiseThreshold(0.5f);
		cAccelmeter.setFilterPerAxis(true);

        fishingBoatData = new FishingBoatData();

//        spawnFish();
	}

    protected void spawnFish() {
        Texture fishTexture = new Texture(Gdx.files.internal("images/SmallFish.png"));
        for(int i = 0; i < 100; i++) {
            SmallFish fish = new SmallFish(fishTexture);
            fish.layout();

            int x = (int) (Math.random() * (Gdx.graphics.getWidth() - fish.getImageWidth()));
            int y = (int) (Math.random() * (Gdx.graphics.getHeight() - fish.getImageHeight()));

            fish.setPosition(x, y);
//            fishList.add(fish);
        }
    }

/*    protected void moveFish() {
        for(int i = 0; i < fishList.size(); i++) {
            SmallFish currentFish = fishList.get(i);

            if(cCraneHitBox.collidesWith(currentFish)) {
                fishList.remove(currentFish);
                System.out.println("EAT");
            }

            if(currentFish.destinationReached()) {
                currentFish.generateDestination();
            }

            currentFish.move();
        }
    }*/

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
//		Vector3 vector = cAccelmeter.update();
//        if(vector.x != 0 || vector.y != 0 ) {
//            double angle = Math.atan2(-vector.y, -vector.x);
//            setHitBoxPosition(cBoatStack, angle);
//            moveBoat(vector);
//            updateCrane(angle);
//        }
//        moveFish();
	}
//
//	protected void moveBoat(Vector3 vector) {
//		float totalForce = Math.abs(vector.x) + Math.abs(vector.y);
//		float newX = cBoatStack.getX();
//		float newY = cBoatStack.getY();
//
//		if(totalForce != 0) {
//			newX = newX - cSpeed*(vector.x / totalForce);
//			newY = newY - cSpeed*(vector.y / totalForce);
//		}
//
//		cBoatStack.setPosition(newX, newY);
//	}
//
//	protected void updateCrane(double angle) {
//        cCrane.setRotation((float)Math.toDegrees(angle));
//    }

}

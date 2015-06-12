package cg.group4.game_logic.stroll.events.multiplayer;

import java.util.ArrayList;
import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.util.sensor.Accelerometer;

public class CraneFishing extends StrollEvent {
	
	protected Accelerometer cAccelmeter;
	protected Stack cBoatStack;
	protected Boat cBoat;
	protected Crane cCrane;
	protected CraneFishingScreen cScreen;
	protected CraneHitbox cCraneHitBox;
	protected int cSpeed = 4;
	protected double cPrevAngle = 0;
	protected ArrayList<SmallFish> fishList;
	protected Circle cHitBox;
	protected int cHitBoxBaseX, cHitBoxBaseY;
	protected int cCraneOffsetX, cCraneOffsetY;
	
	public CraneFishing(CraneFishingScreen screen) {
		super();
		cAccelmeter = new Accelerometer(StandUp.getInstance().getSensorReader());
		cAccelmeter.filterGravity(false);
		cAccelmeter.setNoiseThreshold(1f);
		cAccelmeter.setFilterPerAxis(true);

		fishList = new ArrayList<SmallFish>();
		
		cBoatStack = new Stack();
		cBoatStack.add(new Image(new Texture(Gdx.files.internal("images/BoatBox.png"))));

		cBoatStack.setPosition(240, 440);
		cBoatStack.setSize(128, 128);

        cCraneHitBox = new CraneHitbox(new Texture(Gdx.files.internal("images/HitBox.png")));

        cCraneHitBox.setSize(16,16);

        cPrevAngle = 1.5;
        setHitBoxPosition(cBoatStack, 0);
		
		cBoat = new Boat();
		cCrane = new Crane();

		cBoatStack.add(cBoat);
		cBoatStack.add(cCrane);
        cCrane.setOrigin(cBoatStack.getWidth() /2, cBoatStack.getHeight() / 2);

        cScreen = screen;
		cScreen.getWidgetGroup().debugAll();
		cScreen.getWidgetGroup().addActor(cBoatStack);
		cScreen.getWidgetGroup().addActor(cCraneHitBox);

        spawnFish();
	}
	
	protected void setHitBoxPosition(Stack boat, double angle) {
        angle = angle % (Math.PI * 2);

		float radius = boat.getHeight() / 2;
		float hitBoxRadius = cCraneHitBox.getWidth() / 2;
		float xCoordCenter = boat.getX() + radius;
		float yCoordCenter = boat.getY() + radius;

        float xPosition = (float)Math.cos(angle) * radius;
        float yPosition = (float)Math.sin(angle) * radius;

        cCraneHitBox.setPosition(xCoordCenter + xPosition - hitBoxRadius, yCoordCenter + yPosition - hitBoxRadius);
	}

    protected void spawnFish() {
        Texture fishTexture = new Texture(Gdx.files.internal("images/SmallFish.png"));
        for(int i = 0; i < 100; i++) {
            SmallFish fish = new SmallFish(fishTexture);
            fish.layout();

            int x = (int) (Math.random() * (Gdx.graphics.getWidth() - fish.getImageWidth()));
            int y = (int) (Math.random() * (Gdx.graphics.getHeight() - fish.getImageHeight()));

            fish.setPosition(x, y);
            fishList.add(fish);
            cScreen.getWidgetGroup().addActor(fish);
        }
    }

    protected void moveFish() {
        for(int i = 0; i < fishList.size(); i++) {
            SmallFish currentFish = fishList.get(i);

            if(cCraneHitBox.collidesWith(currentFish)) {
                cScreen.getWidgetGroup().removeActor(currentFish);
                fishList.remove(currentFish);
                System.out.println("EAT");
            }

            if(currentFish.destinationReached()) {
                currentFish.generateDestination();
            }

            currentFish.move();
        }
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
            setHitBoxPosition(cBoatStack, angle);
            moveBoat(vector);
            updateCrane(angle);
        }
        moveFish();
	}
	
	protected void moveBoat(Vector3 vector) {
		float totalForce = Math.abs(vector.x) + Math.abs(vector.y);
		float newX = cBoatStack.getX();
		float newY = cBoatStack.getY();
		
		if(totalForce != 0) {
			newX = newX - cSpeed*(vector.x / totalForce);
			newY = newY - cSpeed*(vector.y / totalForce);
		}
		
		cBoatStack.setPosition(newX, newY);
	}
	
	protected void updateCrane(double angle) {
        cCrane.setRotation((float)Math.toDegrees(angle));
    }
	
	protected void updateHitBox(float angle) {
		int newX = (int) (Math.cos(angle) * (cHitBoxBaseX - (cCrane.getX() + cCrane.getOriginX())) - Math.sin(angle) * (cHitBoxBaseY - (cCrane.getY() + cCrane.getOriginY())) + (cCrane.getX() + cCrane.getOriginX()));
		int newY = (int) (Math.sin(angle) * (cHitBoxBaseX - (cCrane.getX() + cCrane.getOriginX())) + Math.cos(angle) * (cHitBoxBaseY - (cCrane.getY() + cCrane.getOriginY())) + (cCrane.getY() + cCrane.getOriginY()));
		cHitBox.x = newX;
		cHitBox.y = newY;
        cCraneHitBox.setPosition(newX, newY);
	}

}

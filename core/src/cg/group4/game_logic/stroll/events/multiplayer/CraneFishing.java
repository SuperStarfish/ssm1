package cg.group4.game_logic.stroll.events.multiplayer;

import java.util.ArrayList;
import java.util.Observable;

import javax.swing.GroupLayout.Alignment;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.util.sensor.Accelerometer;
import cg.group4.util.sensor.Gyroscope;

public class CraneFishing extends StrollEvent {
	
	protected Accelerometer cAccelmeter;
	protected Stack cBoatStack;
	protected Boat cBoat;
	protected Crane cCrane;
	protected CraneFishingScreen cScreen;
	protected Image x;
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
		cAccelmeter.setNoiseThreshold(0.5f);
		cAccelmeter.setFilterPerAxis(false); 

		fishList = new ArrayList<SmallFish>();
		
		cBoatStack = new Stack();
		cBoatStack.add(new Image(new Texture(Gdx.files.internal("images/BoatBox.png"))));

		cBoatStack.setPosition(120, 220);
		cBoatStack.setSize(64, 64);
		
		x = new Image(new Texture(Gdx.files.internal("images/HitBox.png")));
		x.setSize(8,8);
		
		setHitBoxPosition(cBoatStack, 0);
		
//		cBoat = new Boat();
//		cCrane = new Crane();

//		cBoatStack.add(cBoat);
//		cBoatStack.add(cCrane);
		
		cScreen = screen;
		cScreen.getWidgetGroup().debugAll();
		cScreen.getWidgetGroup().addActor(cBoatStack);
		cScreen.getWidgetGroup().addActor(x);
	}
	
	protected void setHitBoxPosition(Stack boat, float angle) {
		float radius = boat.getHeight() / 2;
		float hitBoxSize = x.getWidth() / 2;
		float xCoordCenter = boat.getX() + radius;
		float yCoordCenter = boat.getY() + radius;

		float xPlacement = (float)Math.cos(Math.toRadians(angle)) * -radius;
		float yPlacement = (float)Math.sin(Math.toRadians(angle)) * radius;
		
		x.setPosition(xCoordCenter - hitBoxSize + xPlacement, yCoordCenter - hitBoxSize + yPlacement);
		
		System.out.println(radius);
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
		Vector3 update = cAccelmeter.update();
//		updateBoat(update);
//		updateCrane(update);
	}
	
	protected void updateBoat(Vector3 update) {
		float totalForce = Math.abs(update.x) + Math.abs(update.y);
		float newX = cBoat.getX();
		float newY = cBoat.getY();
		
		if(totalForce != 0) {
			newX = newX - cSpeed*(update.x / totalForce);
			newY = newY - cSpeed*(update.y / totalForce);
		}
		
		cBoat.setPosition(newX, newY);
		cCrane.setPosition(newX + cCraneOffsetX, newY + cCraneOffsetY);
	}
	
	protected void updateCrane(Vector3 update) {
		double angle = cPrevAngle;
		if(update.x != 0) {
			angle = Math.toDegrees(Math.atan(update.y / update.x));
		}
		
		int extraAngle = 0;
		if(update.x > 0 && update.y > 0) {
			extraAngle = 90;
		} else if(update.x < 0 && update.y > 0) {
			extraAngle = 180;
		} else if(update.x < 0 && update.y < 0) {
			extraAngle = 270;
		}
		angle += extraAngle;
		cCrane.setRotation((float) -cPrevAngle);
		cCrane.setRotation((float) angle);
		cPrevAngle = angle;
		updateHitBox((float) angle);
	}
	
	protected void updateHitBox(float angle) {
		int newX = (int) (Math.cos(angle) * (cHitBoxBaseX - (cCrane.getX() + cCrane.getOriginX())) - Math.sin(angle) * (cHitBoxBaseY - (cCrane.getY() + cCrane.getOriginY())) + (cCrane.getX() + cCrane.getOriginX()));
		int newY = (int) (Math.sin(angle) * (cHitBoxBaseX - (cCrane.getX() + cCrane.getOriginX())) + Math.cos(angle) * (cHitBoxBaseY - (cCrane.getY() + cCrane.getOriginY())) + (cCrane.getY() + cCrane.getOriginY()));
		cHitBox.x = newX;
		cHitBox.y = newY;
		x.setPosition(newX, newY);
	}

}

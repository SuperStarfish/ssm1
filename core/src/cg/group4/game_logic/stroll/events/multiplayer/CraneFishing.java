package cg.group4.game_logic.stroll.events.multiplayer;

import java.util.Observable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.util.sensor.Accelerometer;
import cg.group4.util.sensor.Gyroscope;

public class CraneFishing extends StrollEvent {
	
	protected Accelerometer cAccelmeter;
	protected Image cBoat, cCrane;
	protected CraneFishingScreen cScreen;
	
	private Circle craneCollisionCircle;
	
	public CraneFishing(CraneFishingScreen screen) {
		super();
		cAccelmeter = new Accelerometer(StandUp.getInstance().getSensorReader());
		cAccelmeter.filterGravity(false);
		//craneCollisionCircle = new Circle();
		//craneCollisionCircle.setRadius(5.5f);
		//craneCollisionCircle.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		
		cCrane = new Image(new Texture(Gdx.files.internal("images/Crane.png")));
		cCrane.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		cCrane.layout();
		cCrane.setOrigin(cCrane.getImageWidth() / 2, cCrane.getImageHeight() / 2);
		cScreen = screen;
		cScreen.getWidgetGroup().addActor(cCrane);
	}

	@Override
	public void update(Observable o, Object arg) {
		Vector3 update = cAccelmeter.update();
		float speed = 2;
		
		float totalForce = Math.abs(update.x) + Math.abs(update.y);
		System.out.println("TOTAL FORCE: " + totalForce);
		
		float currentX = cCrane.getX();
		float currentY = cCrane.getY(); 
		
		float newX = currentX - speed*(update.x / totalForce);
		float newY = currentY - speed*(update.y / totalForce);
		
		System.out.println("OLD X: " + currentX + " OLD Y: " + currentY);
		System.out.println("NEW X: " + (currentX + speed*(update.x / totalForce)) + " NEW Y: " + (currentY + speed*(update.y / totalForce)));
		
		if(Float.isNaN(newX)) {
			newX = currentX;
		}
		if(Float.isNaN(newY)) {
			newY = currentY;
		}
		cCrane.setPosition(newX, newY);
		
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

}

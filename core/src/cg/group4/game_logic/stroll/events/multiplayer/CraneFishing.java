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
	
	protected Gyroscope cGyro;
	protected Image cBoat, cCrane;
	protected CraneFishingScreen cScreen;
	
	private Circle craneCollisionCircle;
	
	protected float cPreviousAzimuth;
	
	public CraneFishing(CraneFishingScreen screen) {
		super();
		cGyro = new Gyroscope(StandUp.getInstance().getSensorReader());
		//craneCollisionCircle = new Circle();
		//craneCollisionCircle.setRadius(5.5f);
		//craneCollisionCircle.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		
		cCrane = new Image(new Texture(Gdx.files.internal("images/Crane.png")));
		cCrane.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		cCrane.layout();
		cCrane.setOrigin(cCrane.getImageWidth() / 2, cCrane.getImageHeight() / 2);
		cScreen = screen;
		cScreen.getWidgetGroup().addActor(cCrane);
		cPreviousAzimuth = cGyro.update().z;
	}

	@Override
	public void update(Observable o, Object arg) {
		float azimuthRotation = cGyro.update().z;
		if(azimuthRotation - cPreviousAzimuth > 1.5) {
			System.out.println("AZI: " + (azimuthRotation - cPreviousAzimuth));
			cCrane.rotateBy(azimuthRotation - cPreviousAzimuth);
		}
		cPreviousAzimuth = azimuthRotation;
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

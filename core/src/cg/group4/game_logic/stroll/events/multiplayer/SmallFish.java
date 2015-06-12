package cg.group4.game_logic.stroll.events.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SmallFish extends Image {
	
	protected int cDestinationX, cDestinationY;
	protected int cSpeed = 4;
	
	public SmallFish(Texture t) {
		super(t);
		generateDestination();
	}
	
	public void generateDestination() {
		cDestinationX = (int) (Math.random() * (Gdx.graphics.getWidth() - this.getImageWidth()));
		cDestinationY = (int) (Math.random() * (Gdx.graphics.getHeight() - this.getImageHeight()));
	}
	
	public boolean destinationReached() {
		boolean xCheck = cDestinationX <= (this.getX() + this.getImageWidth()) && cDestinationX >= this.getX();
		boolean yCheck = cDestinationY <= (this.getY() + this.getImageHeight()) && cDestinationY >= this.getY();
		
		return xCheck && yCheck;
	}
	
	public void move() {
		int diffX = (int) (cDestinationX - this.getX());
		int diffY = (int) (cDestinationY - this.getY());
		
		int newX = (int) (this.getX() + (cSpeed * Math.signum(diffX)));
		int newY = (int) (this.getY() + (cSpeed * Math.signum(diffY)));
//		System.out.println("CURRENTX: " + this.getX() + " CURRENTY: " + this.getY());
//		System.out.println("NEWX: " + newX + " NEWY: " + newY);
//		System.out.println("DESTINATION: X: " + cDestinationX + " Y: " + cDestinationY);
		this.setPosition(newX, newY);
	}

	public float getCenterX() {
		return this.getX() + this.getWidth() / 2;
	}

	public float getCenterY() {
		return this.getY() + this.getHeight() / 2;
	}
}

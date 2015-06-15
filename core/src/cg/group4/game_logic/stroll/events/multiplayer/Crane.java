package cg.group4.game_logic.stroll.events.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Crane extends Image {
	
	public Crane() {
		super(new Texture(Gdx.files.internal("images/Crane.png")));
		this.layout();
		
		final int radius = (int) this.getImageWidth() / 2;
		this.setOrigin(radius, radius);
	}

}

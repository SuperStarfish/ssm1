package cg.group4.game_logic.stroll.events.multiplayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Boat extends Image {
	
	public Boat() {
		super(new Texture(Gdx.files.internal("images/Boat.png")));
		this.layout();
		
		this.setOrigin((int) this.getImageWidth() / 2, (int) this.getImageHeight() / 2);
	}
}

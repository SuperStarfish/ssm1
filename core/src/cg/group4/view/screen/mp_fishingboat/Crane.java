package cg.group4.view.screen.mp_fishingboat;

import cg.group4.view.screen_mechanics.Assets;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

/**
 * Displays the Crane image.
 */
public class Crane extends Image {
    /**
     * Creates a new Crane and sets the origin to the center of the wrapping Stack.
     * @param stack The wrapping Stack.
     */
	public Crane(Stack stack) {
		super(Assets.getInstance().getTexture("images/Crane.png"));
		this.layout();
		
		final int center = (int) (stack.getWidth() / 2f);
		this.setOrigin(center, center);
	}

}

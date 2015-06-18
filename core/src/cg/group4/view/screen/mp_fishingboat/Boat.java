package cg.group4.view.screen.mp_fishingboat;

import cg.group4.view.screen_mechanics.Assets;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

/**
 * Displays the boat image.
 */
public class Boat extends Image {
    /**
     * Creates a new boat and sets the origin to the center of the wrapping Stack.
     * @param stack The wrapping Stack.
     */
	public Boat(Stack stack) {
		super(Assets.getInstance().getTexture("images/Boat.png"));
		this.layout();

        final int center = (int) (stack.getWidth() / 2f);
        this.setOrigin(center, center);
	}
}

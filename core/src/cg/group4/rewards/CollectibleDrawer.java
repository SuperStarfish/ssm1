package cg.group4.rewards;

import cg.group4.rewards.collectibles.Collectible;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;

/**
 * Class that creates images from collectibles and returns these created images.
 * @author Jean de Leeuw
 *
 */
public class CollectibleDrawer {
	
	/**
	 * Colour in the image that needs to be replaced.
	 */
	protected final float[] cReplacementColour = {120f/255f, 120f/255f, 120f/255f};
	
	/**
	 * Alpha value of the new colour.
	 */
	protected final float cAlpha = 1f;
	
	/**
	 * Returns an image of the given collectible.
	 * 
	 * @param c Collectible that needs to be drawn.
	 * @return Texture image with the collectible.
	 */
	public final Texture drawCollectible(final Collectible c) {
		Pixmap pixImage = new Pixmap(Gdx.files.internal(c.getImagePath()));
		replaceColours(cReplacementColour, c.getColour(), pixImage);
		
		return new Texture(pixImage);
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Method that replaces pixels with a certain colour with another colour.
	 * 
	 * @param replaceColour Colour that needs to be replaced. (RGB)
	 * @param newColour Colour that replaceColour will be replaced with. (RGB)
	 * @param pix Pixmap containing the image that needs colour replacement.
	 */
	protected final void replaceColours(final float[] replaceColour, final float[] newColour, final Pixmap pix) {
		pix.setColor(newColour[0], newColour[1], newColour[2], cAlpha);
		
		for (int y = 0; y < pix.getHeight(); y++) {
			for (int x = 0; x < pix.getWidth(); x++) {
				
				Color currentColour = new Color(pix.getPixel(x, y));
				float[] currentColourRGB = {
						currentColour.r,
						currentColour.g,
						currentColour.b};
				
				if (sameColour(currentColourRGB, replaceColour)) {
					pix.drawPixel(x, y);
				}
			}
		}
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Method that checks if two colours are the same colour.
	 * 
	 * @param color1 (RGB)
	 * @param color2 (RGB)
	 * @return boolean indicating if the two colours are the same.
	 */
	protected final boolean sameColour(final float[] color1, final float[] color2) {
		boolean red = (color1[0] == color2[0]);
		boolean green = (color1[1] == color2[1]);
		boolean blue = (color1[2] == color2[2]);
		
		return red && green && blue;
	}
}

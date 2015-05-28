package cg.group4.rewards.collectibles;


/**
 * Forces collectible objects to implement the following methods.
 * @author Jean de Leeuw
 *
 */
public abstract class Collectible {
	
	/**
	 * Every collectible must have a colour.
	 * @return float[] containing the RGB colours [0-1]
	 */
	public abstract float[] getColour();
	
	/**
	 * Every collectible must have a corresponding image of its form.
	 * @return path to the location of the image inside the assets.
	 */
	public abstract String getImagePath();
}

package cg.group4.rewards.collectibles;


import java.io.Serializable;

/**
 * A collectible with variable colour that can be generated.
 * @author Jean de Leeuw
 *
 */
public class FishA extends Collectible implements Serializable {
	
	/**
	 * Constructs a FishA collectible.
	 * @param hue representing the colour of the collectible
	 */
	public FishA(final float hue) {
		super(hue);
	}

	@Override
	public String getImagePath() {
		return "images/FishA.png";
	}

	@Override
	public float getFormRarity() {
		final float formRarity = 0.1f;
		return formRarity;
	}
}

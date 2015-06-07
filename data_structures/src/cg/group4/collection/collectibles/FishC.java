package cg.group4.collection.collectibles;


import java.io.Serializable;

/**
 * A collectible with variable colour that can be generated.
 * @author Jean de Leeuw
 *
 */
public class FishC extends Collectible implements Serializable {

	/**
	 * The rarity of this collectible.
	 */
	protected final float cFormRarity = 1f;
	
	/**
	 * Constructs a FishA collectible.
	 * @param hue representing the colour of the collectible.
	 */
	public FishC(final float hue) {
		super(hue);
	}

	@Override
	public String getImagePath() {
		return "images/FishC.png";
	}

	@Override
	public float getFormRarity() {
		return cFormRarity;
	}

}

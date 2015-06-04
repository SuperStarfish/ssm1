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
	 * @param wavelength representing the colour of the collectible
	 */
	public FishA(final int wavelength) {
		super(wavelength);
	}

	@Override
	public String getImagePath() {
		return "images/FishA.png";
	}

	@Override
	public double getFormRarity() {
		final double formRarity = 0.3;
		return formRarity;
	}
}

package cg.group4.rewards.collectibles;


/**
 * A collectible with variable colour that can be generated.
 * @author Jean de Leeuw
 *
 */
public class FishA extends Collectible {
	
	/**
	 * Constructs a FishA collectible.
	 * @param wavelength representing the colour of the collectible
	 */
	public FishA(final int wavelength) {
		super(wavelength);
	}

	/**
	 * Constructs a FishA collectible.
	 * @param wavelength representing the colour of the collectible.
	 */
	

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

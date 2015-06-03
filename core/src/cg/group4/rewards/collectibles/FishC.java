package cg.group4.rewards.collectibles;


/**
 * A collectible with variable colour that can be generated.
 * @author Jean de Leeuw
 *
 */
public class FishC extends Collectible {
	
	/**
	 * Constructs a FishA collectible.
	 * @param wavelength representing the colour of the collectible.
	 */
	public FishC(final int wavelength) {
		super(wavelength);
	}

	@Override
	public String getImagePath() {
		return "images/FishC.png";
	}

	@Override
	public double getFormRarity() {
		final double formRarity = 0.7;
		return formRarity;
	}

}

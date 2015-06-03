package cg.group4.rewards.collectibles;


/**
 * A collectible with variable colour that can be generated.
 * @author Jean de Leeuw
 *
 */
public class FishB extends Collectible {
	
	/**
	 * Constructs a FishA collectible.
	 * @param wavelength representing the colour of the collectible.
	 */
	public FishB(final int wavelength) {
		super(wavelength);
	}

	@Override
	public String getImagePath() {
		return "images/FishB.png";
	}

	@Override
	public double getFormRarity() {
		final double formRarity = 0.5;
		return formRarity;
	}
}

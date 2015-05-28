package cg.group4.rewards.collectibles;

import cg.group4.rewards.WavelengthToRGB;

/**
 * A collectible with variable colour that can be generated.
 * @author Jean de Leeuw
 *
 */
public class FishC extends Collectible {
	
	/**
	 * Location of the image of the form of the collectible.
	 */
	protected final String cImageLocation = "images/FishC.png";
	
	/**
	 * Wavelength representing the colour of the collectible.
	 */
	protected final int cWavelength;
	
	/**
	 * Constructs a FishA collectible.
	 * @param wavelength representing the colour of the collectible.
	 */
	public FishC(final int wavelength) {
		cWavelength = wavelength;
	}

	@Override
	public float[] getColour() {
		return WavelengthToRGB.wavelengthToRGB(cWavelength);
	}

	@Override
	public String getImagePath() {
		return cImageLocation;
	}
}

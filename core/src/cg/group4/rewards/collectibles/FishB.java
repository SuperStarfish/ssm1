package cg.group4.rewards.collectibles;

import java.util.Date;

import cg.group4.rewards.WavelengthToRGB;

/**
 * A collectible with variable colour that can be generated.
 * @author Jean de Leeuw
 *
 */
public class FishB extends Collectible {

	/**
	 * Location of the image of the form of the collectible.
	 */
	protected final String cImageLocation = "images/FishB.png";
	
	/**
	 * Wavelength representing the colour of the collectible.
	 */
	protected final int cWavelength;
	
	/**
	 * Constructs a FishA collectible.
	 * @param wavelength representing the colour of the collectible.
	 */
	public FishB(final int wavelength) {
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

	@Override
	public Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getRarity() {
		// TODO Auto-generated method stub
		return 1.0;
	}

	@Override
	public int getAmount() {
		// TODO Auto-generated method stub
		return 0;
	}
}

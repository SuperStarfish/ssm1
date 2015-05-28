package cg.group4.rewards;

/**
 * Utility class that converts a wavelength to a RGB colour.
 * @author Jean de Leeuw
 *
 *Sources:
 *http://www.efg2.com/Lab/ScienceAndEngineering/Spectra.htm
 *http://stackoverflow.com/questions/1472514/convert-light-frequency-to-rgb
 */
public final class WavelengthToRGB {
	
	/**
	 * Hidden constructor because it is a utility class.
	 */
	private WavelengthToRGB() {
	}
	
	/**
	 * Gamma value.
	 */
	protected static final double cGamma = 0.80d;
	
	/**
	 * Max value of RGB.
	 */
	protected static final int cMaxIntensity = 255;
	
	/**
	 * Colour interval values of the visible spectrum.
	 */
	protected static final int cCinterval1 = 380, 
			cCinterval2 = 440,
			cCinterval3 = 490,
			cCinterval4 = 510,
			cCinterval5 = 580,
			cCinterval6 = 645,
			cCinterval7 = 781;
	
	/**
	 * Intensity interval values.
	 */
	protected static final int cIinterval1 = 380,
			cIinterval2 = 420,
			cIinterval3 = 700,
			cIinterval4 = 780;
	
	/**
	 * Returns the RGB values of the given wavelength.
	 * 
	 * @param wavelength in the visible spectrum (between 380 - 780)
	 * @return RGB colours corresponding to the given wavelength
	 */
	public static int[] wavelengthToRGB(final double wavelength) {
		int[] RGB = new int[3];
		
		double[] colours = setColours(wavelength);
		double intensity = determineIntensity(wavelength);
		
		RGB[0] = adjustColours(colours[0], intensity);
		RGB[1] = adjustColours(colours[1], intensity);
		RGB[2] = adjustColours(colours[2], intensity);
		
		return RGB;
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Returns the initial RGB values of the given wavelength.
	 *  
	 * @param wavelength in the visible spectrum (between 380 - 780)
	 * @return Initial RGB colours corresponding to the given wavelength
	 */
	protected static double[] setColours(final double wavelength) {
		double[] RGB = {0d, 0d, 0d};
		
		if (wavelength >= cCinterval1 && wavelength < cCinterval2) {
			RGB[0] = -(wavelength - cCinterval2) / (cCinterval2 - cCinterval1);
			RGB[1] = 0d;
			RGB[2] = 1d;
		} else if (wavelength >= cCinterval2 && wavelength < cCinterval3) {
			RGB[0] = 0d;
			RGB[1] = (wavelength - cCinterval2) / (cCinterval3 - cCinterval2);
			RGB[2] = 1d;
		} else if (wavelength >= cCinterval3 && wavelength < cCinterval4) {
			RGB[0] = 0d;
			RGB[1] = 1d;
			RGB[2] = -(wavelength - cCinterval4) / (cCinterval4 - cCinterval3);
		} else if (wavelength >= cCinterval4 && wavelength < cCinterval5) {
			RGB[0] = (wavelength - cCinterval4) / (cCinterval5 - cCinterval4);
			RGB[1] = 1d;
			RGB[2] = 0d;
		} else if (wavelength >= cCinterval5 && wavelength < cCinterval6) {
			RGB[0] = 1d;
			RGB[1] = -(wavelength - cCinterval6) / (cCinterval6 - cCinterval5);
			RGB[2] = 0d;
		} else if (wavelength >= cCinterval6 && wavelength < cCinterval7) {
			RGB[0] = 1d;
			RGB[1] = 0d;
			RGB[2] = 0d;
		}
		return RGB;
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Determines the intensity of the given wavelength
	 * (Intensity falls of near the end of the visible spectrum).
	 * 
	 * @param wavelength in the visible spectrum (between 380 - 780)
	 * @return Intensity value
	 */
	protected static double determineIntensity(final double wavelength) {
		double factor = 0d;
		final double factor1 = 0.3, factor2 = 0.7;
		
		if (wavelength >= cIinterval1 && wavelength < cIinterval2) {
			factor = factor1 + factor2 * (wavelength - cIinterval1) / (cIinterval2 - cIinterval1);
		} else if (wavelength >= cIinterval2 && wavelength < cIinterval3 + 1) {
			factor = 1d;
		} else if (wavelength >= cIinterval3 + 1 && wavelength < cIinterval4 + 1) {
			factor = factor1 + factor2 * (cIinterval4 - wavelength) / (cIinterval4 - cIinterval3);
		}
		return factor;
	}
	
	/**
	 *  Helper method that should not be called outside of this class.
	 *  Adjusts the initial calculated RGB values to the correct RGB values.
	 *  
	 * @param colour initial RGB values
	 * @param factor intensity value
	 * @return Correct RGB values
	 */
	protected static int adjustColours(final double colour, final double factor) {
		if (colour == 0d) {
			return 0;
		}
		return (int) Math.round(cMaxIntensity * Math.pow(colour * factor, cGamma));
	}
}

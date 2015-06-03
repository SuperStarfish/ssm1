package cg.group4.rewards.collectibles;

import java.util.Date;

import cg.group4.rewards.RewardUtil;


/**
 * Forces collectible objects to implement the following methods.
 * @author Jean de Leeuw
 *
 */
public abstract class Collectible {
	
	/**
	 * Wavelength of the collectible representing the colour of the collectible.
	 */
	protected int cWavelength;
	
	/**
	 * Most recent date on which a collectible of a certain kind (colour and form) has been obtained.
	 */
	protected Date cData;
	
	/**
	 * Amount of collectibles that you have of the same kind. (Same colour and form)
	 */
	protected int cAmount;
	
	/**
	 * Multiplier that represents the rarity of this form of collectible.
	 * (The higher multiplier, the more rare this form becomes and thus the more rare
	 * this collectible becomes)
	 */
	protected double cFormMultiplier;
	
	/**
	 * Constructs a FishA collectible.
	 * @param wavelength representing the colour of the collectible
	 */
	public Collectible(final int wavelength) {
		assert (wavelength >= 380 && wavelength <= 780);
		cWavelength = wavelength;
	}
	
	/**
	 * Every collectible must have a colour.
	 * @return float[] containing the RGB colours [0-1]
	 */
	public float[] getColour() {
		return RewardUtil.wavelengthToRGB(cWavelength);
	}
	
	/**
	 * Every collectible must have a corresponding image of its form.
	 * @return path to the location of the image inside the assets.
	 */
	public abstract String getImagePath();
	
	/**
	 * Every collectible must have the date on which it was obtained.
	 * This date is refreshed every time you get a new instance of the same collectible.
	 * @return Date representing the date on which the collectible was obtained.
	 */
	public Date getDate() {
		return null;
	}
	
	/**
	 * The rarity of every collectible can be calculated based on its colour and shape.
	 * @return Double representing the rarity of the collectible.
	 */
	public double getRarity() {
		return getFormRarity() * RewardUtil.getColorRarity(cWavelength);
	}
	
	/**
	 * Multiple instances of the same collectible is possible, so every collectible
	 * has a counter of how many of this collectible you have.
	 * @return Int representing the amount of collectibles of this type that you have.
	 */
	public int getAmount() {
		return 0;
	}
	
	/**
	 * Returns the multiplier that represents the rarity of this form of collectible.
	 * (The higher multiplier, the more rare this form becomes and thus the more rare
	 * this collectible becomes)
	 * @return double representing the form multiplier.
	 */
	public abstract double getFormRarity();
}

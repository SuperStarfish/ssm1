package cg.group4.rewards.collectibles;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import cg.group4.rewards.RewardUtil;


/**
 * Forces collectible objects to implement the following methods.
 * @author Jean de Leeuw
 *
 */
public abstract class Collectible implements Serializable {
	
	/**
	 * Wavelength of the collectible representing the colour of the collectible.
	 */
	protected int cWavelength;
	
	/**
	 * Most recent date on which a collectible of a certain kind (colour and form) has been obtained.
	 */
	protected Date cDate;

	final int minWaveLength = 380;
	
	final int maxWaveLength = 780;
	
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
	 * Constructs a collectible.
     * The constructed collectible will be based on its shape (the implementing class) and a colour (specified here).
     * The colour is based on the wavelength of light. The wavelength is clamped between 380 - 780 inclusive.
	 * @param wavelength representing the colour of the collectible
	 */
	public Collectible(final int wavelength) {
        cWavelength = clampWaveLength(wavelength);
        cDate = new Date();
        cAmount = 1;
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
		return cDate;
	}

    public String getDateAsString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cDate);
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
		return cAmount;
	}
	
	/**
	 * Returns the multiplier that represents the rarity of this form of collectible.
	 * (The higher multiplier, the more rare this form becomes and thus the more rare
	 * this collectible becomes)
	 * @return double representing the form multiplier.
	 */
	public abstract double getFormRarity();

    /**
     * Keeps the wave length between the visible human color range.
     * The supported range is 380 - 780 (inclusive).
     * @param waveLength Input to be clamped
     * @return The clamped wavelength based on the input parameter
     */
    private int clampWaveLength(final int waveLength) {
        int res = waveLength;

        // clamp lower bound
        if (waveLength < minWaveLength) {
            res = minWaveLength;
        }
        // clamp upper bound
        else if (waveLength > maxWaveLength) {
            res = maxWaveLength;
        }

        return res;
    }

    public int getcWavelength() {
        return cWavelength;
    }

	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Collectible<");
        sb.append("wavelength = ").append(cWavelength).append(", ");
        sb.append("amount = ").append(getAmount()).append(", ");
        sb.append("form multiplier = ").append(getFormRarity());
        sb.append(">");
        return sb.toString();
    }

}

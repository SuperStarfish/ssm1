package cg.group4.rewards.collectibles;

import cg.group4.rewards.RewardUtil;
import com.badlogic.gdx.graphics.Color;

import java.io.Serializable;
import java.util.Date;


/**
 * Forces collectible objects to implement the following methods.
 * @author Jean de Leeuw
 *
 */
public abstract class Collectible implements Serializable {
	
	/**
	 * Wavelength of the collectible representing the colour of the collectible.
	 */
	protected float cHue;
	
	/**
	 * Most recent date on which a collectible of a certain kind (colour and form) has been obtained.
	 */
	protected Date cDate;
	
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
	 * @param hue representing the colour of the collectible
	 */
	public Collectible(final float hue) {
		cHue = hue;
	}
	
	/**
	 * Every collectible must have a colour.
	 * @return float[] containing the RGB colours [0-1]
	 */
	public Color getColour() {
		return RewardUtil.generateColor(cHue);
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
		return cHue;
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
	public abstract float getFormRarity();

	/**
	 * Returns a string representation of a collectible, including some of it's values.
     * @return String string representation of the collectible
     */
	public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Collectible<");
		sb.append("wavelength = ").append(cHue).append(", ");
		sb.append("amount = ").append(getAmount()).append(", ");
		sb.append("form multiplier = ").append(getFormRarity());
		sb.append(">");
        return sb.toString();
    }

}

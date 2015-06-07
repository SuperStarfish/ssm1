package cg.group4.collection.collectibles;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
     * The owner who originally got the collectible.
     */
    protected String cOwnerId;

    /**
     * Constructs a collectible.
     * The constructed collectible will be based on its shape (the implementing class) and a colour (specified here).
	 * @param hue representing the colour of the collectible
     */
    public Collectible(final float hue, final String ownerId) {
        cHue = hue;
        cDate = new Date();
        cAmount = 1;
        cOwnerId = ownerId;
    }

    public Collectible(final float hue, final int amount, final Date date, final String ownerId) {
        cHue = hue;
        cAmount = amount;
        cDate = date;
        cOwnerId = ownerId;
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

	/**
	 * Returns the date as a string that is properly formatted for the server.
	 * @return yyyy-MM-dd
	 */
    public String getDateAsString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cDate);
    }
	
	/**
	 * The rarity of every collectible can be calculated based on its colour and shape.
	 * @return Double representing the rarity of the collectible.
	 */
	public double getRarity() {
		return getFormRarity() * cHue * 100;
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
     * Increments the amount of this collectible.
     */
    public void incrementAmount() {
        cAmount++;
    }

    /**
     * Returns the multiplier that represents the rarity of this form of collectible.
	 * (The higher multiplier, the more rare this form becomes and thus the more rare
	 * this collectible becomes)
	 * @return double representing the form multiplier.
	 */
	public abstract float getFormRarity();

    /**
     * Returns the Hue of this collectible.
     * @return The hue.
     */
	public float getHue() {
		return cHue;
    }

    /**
     * Returns the owner id of this collectible.
     *
     * @return The owner id.
     */
    public String getOwnerId() {
        return cOwnerId;
    }

    /**
     * Returns a string representation of a collectible, including some of it's values.
     * @return String string representation of the collectible
     */
	public String toString() {
        return "Collectible<" + "hue = " + cHue + ", " + "amount = " + getAmount() +
                ", " + "form rarity = " + getFormRarity() + ">";
    }

}

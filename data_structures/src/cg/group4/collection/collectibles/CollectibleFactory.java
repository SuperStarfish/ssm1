package cg.group4.collection.collectibles;

import java.util.Date;

/**
 * Creates and returns collectible objects.
 * @author Jean de Leeuw
 *
 */
public class CollectibleFactory {
	
	/**
	 * List containing the names of all the possible collectibles.
	 * Used by the rewardgenerator to randomly pick one.
	 */
	protected String[] cCollectiblesList = {
			Collectibles.FishA.toString(),
			Collectibles.FishB.toString(),
			Collectibles.FishC.toString()};
	
	/**
	 * Generates a collectible of the class of the given string, with colour wavelength.
	 * 
	 * @param collectible represents the class of the collectible
	 * @param hue represent colour of the collectible.
	 * @param ownerId The owner of the collectible.
	 * @return Collectible object
	 */
	public final Collectible generateCollectible(final String collectible, final float hue, final String ownerId) {
		Collectible result = null;
		
		switch(collectible) {
			case "FishA":
				result = new FishA(hue, ownerId);
			break;
		case "FishB":
			result = new FishB(hue, ownerId);
			break;
		case "FishC":
			result = new FishC(hue, ownerId);
			break;
		default:
			break;
		}

		return result;
	}

	public final Collectible generateCollectible(final String collectible, final float hue, final int amount,
												 final Date date, final String ownerId) {
		Collectible result = null;

		switch (collectible) {
			case "FishA":
				result = new FishA(hue, amount, date, ownerId);
				break;
			case "FishB":
				result = new FishB(hue, amount, date, ownerId);
				break;
			case "FishC":
				result = new FishC(hue, amount, date, ownerId);
				break;
			default:
				break;
		}

		return result;
	}
	
	/**
	 * Gets the list of the names of all the possible collectibles.
	 * @return String[] containing all the names.
	 */
	public final String[] getCollectiblesList() {
		return cCollectiblesList;
	}
	
	/**
	 * Contains all the possible collectibles.
	 * @author Jean de Leeuw
	 *
	 */
	public enum Collectibles {
		/**
		 * Collectible FishA.
		 */
		FishA,
		
		/**
		 * Collectible FishB.
		 */
		FishB,
		
		/**
		 * Collectible FishC.
		 */
		FishC
	}
}

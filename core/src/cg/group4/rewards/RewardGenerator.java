package cg.group4.rewards;

import java.util.Random;

import cg.group4.rewards.collectibles.Collectible;
import cg.group4.rewards.collectibles.CollectibleFactory;

/**
 * Objects that generates the rewards.
 * @author Jean de Leeuw
 *
 */
public class RewardGenerator {
	
	/**
	 * Generates random numbers used in the generation of the rewards.
	 */
	protected Random cRNG;
	
	/**
	 * Numbers of possible different colours.
	 */
	protected final int cDifferentColours = 400;
	
	/**
	 * Number of possible different numbers the random number generator can generate.
	 */
	protected final int cRandomNumberPossibilities = 400000000;
	
	/**
	 * Upper limit of range of numbers that can be generated (not inclusive).
	 */
	protected final double cRangeMax;
	
	/**
	 * Lower limit of range of numbers that can be generated (inclusive).
	 */
	protected final double cRangeMin;
	
	/**
	 * Object that creates collectibles.
	 */
	protected final CollectibleFactory cCollectibleFactory;
	
	/**
	 * Constructs a new reward generator.
	 */
	public RewardGenerator() {
		cRNG = new Random();
		cRangeMax = inverseRewardFunction(cDifferentColours);
		cRangeMin = inverseRewardFunction(0);
		cCollectibleFactory = new CollectibleFactory();
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Generates a random collectible and return this created collectible.
	 * @return Collectible object.
	 */
	protected final Collectible generateOneCollectible() {
		String[] collectibleList = cCollectibleFactory.getCollectiblesList();
		int nr = cRNG.nextInt(collectibleList.length);
		int colour = generateRewardColour();
		return cCollectibleFactory.generateCollectible(collectibleList[nr], colour);
	}
	
	/**
	 * Generates a collectible based on the score that was earned in the event.
	 * The higher the score, the more chance on a more rare collectible.
	 * @param eventScore score earned during the event.
	 * @return Collectible object.
	 */
	public final Collectible generateCollectible(final int eventScore) {
		Collectible mostValuable = generateOneCollectible();
		
		for (int i = 0; i < eventScore - 1; i++) {
			Collectible c = generateOneCollectible();
			if (c.getRarity() > mostValuable.getRarity()) {
				mostValuable = c;
			}
		}
		return mostValuable;
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Generates the colour of the reward.
	 * 
	 * @return RGB values of the colour of the reward. 
	 */
	protected final int generateRewardColour() {
		double random = randomDouble();
		random = rewardFunction(random);
		
		final int lowerEndVisibleSpectrum = 380;
		int wavelength = (int) (cDifferentColours - random) + lowerEndVisibleSpectrum;
		return wavelength;
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Generates a random double between ranges cRangeMin and cRangeMax with cRandomNumberPossiblities possible values. 
	 * 
	 * @return Random Double
	 */
	protected final double randomDouble() {
		int nr = cRNG.nextInt(cRandomNumberPossibilities);
		
		double range = cRangeMax - cRangeMin;
		double perStep = (double) range / cRandomNumberPossibilities;
		return nr * perStep;
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Applies a function to the input.
	 * 
	 * The reward function now is specified as: f(x) = x^4.
	 * @param x Input
	 * @return Result after applying the function to the input
	 */
	protected final double rewardFunction(final double x) {
		final int exponent = 4;
		return Math.pow(x, exponent);
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Applies the inverse of the above function to the input.
	 * 
	 * The inverse of the reward function now is specified as: f(x) = x^(1/4).
	 * @param x Input
	 * @return Result after applying the inverse of the function to the input
	 */
	protected final double inverseRewardFunction(final double x) {
		final int exponent = 4;
		return Math.pow(x, (double) 1 / exponent);
	}
}

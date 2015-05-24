package cg.group4.rewards;

import java.util.Random;

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
	protected final int differentColours = 400;
	
	/**
	 * Number of possible different numbers the random number generator can generate.
	 */
	protected final int cRandomNumberPossibilities = 400000000;
	
	/**
	 * Upper limit of range of numbers that can be generated (not inclusive).
	 */
	protected final int cRangeMax = 20;
	
	/**
	 * Lower limit of range of numbers that can be generated (inclusive).
	 */
	protected final int cRangeMin = 0;
	
	/**
	 * Constructs a new reward generator.
	 */
	public RewardGenerator() {
		cRNG = new Random();
	}
	
	/**
	 * Generates the colour of the reward.
	 * @return RGB values of the colour of the reward. 
	 */
	public final int[] generateRewardColor() {
		double random = randomDouble();
		random = rewardFunction(random);
		
		final int lowerEndVisibleSpectrum = 380;
		double wavelength = (differentColours - random) + lowerEndVisibleSpectrum;
		return WavelengthToRGB.wavelengthToRGB(wavelength);
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Generates a random double between ranges cRangeMin and cRangeMax with cRandomNumberPossiblities possible values. 
	 * 
	 * @return Random Double
	 */
	protected final double randomDouble() {
		int nr = cRNG.nextInt(cRandomNumberPossibilities);
		
		int range = cRangeMax - cRangeMin;
		double perStep = (double) range / cRandomNumberPossibilities;
		return nr * perStep;
	}
	
	/**
	 * Helper method that should not be called outside of this class.
	 * Applies a function to the input.
	 * 
	 * @param x Input
	 * @return Result after applying the function to the input.
	 */
	protected final double rewardFunction(final double x) {
		final int exponent = 4;
		return Math.pow(x, exponent);
	}
}

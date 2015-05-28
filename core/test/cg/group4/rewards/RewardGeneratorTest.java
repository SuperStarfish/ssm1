package cg.group4.rewards;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Tests to ensure proper behaviour of the RewardGenerator
 * @author Jean de Leeuw
 *
 */
public class RewardGeneratorTest {
	
	/**
	 * Instance of RewardGenerator to test.
	 */
	protected RewardGenerator cGenerator;
	
	/**
	 * Mock of the random generator so we can feed the reward generator custom input.
	 */
	protected Random cRandom;
	
	/**
	 * Error margin allowed in the tests.
	 */
	protected final double cErrorMargin = 0.000001;
	
	/**
	 * Constructs the mock (but does not assign it yet, this happens in the tests themselves)
	 * and constructs the reward generator to test.
	 */
	@Before
	public void setUp() {
		cGenerator = new RewardGenerator();
		cRandom = Mockito.mock(Random.class);
	}
	
	/**
	 * Destroys the mock and reward generator so every test works with clean objects.
	 */
	@After
	public void tearDown() {
		cGenerator = null;
		cRandom = null;
	}
	
	/**
	 * Tests if all the attributes/fields are set correctly.
	 */
	@Test
	public void fieldsTest() {
		final int colourAmount = 400,
				randomPossibilities = 400000000,
				minRange = 0;
		final double maxRange = 4.472136;
		assertEquals(colourAmount, cGenerator.differentColours, 0);
		assertEquals(randomPossibilities, cGenerator.cRandomNumberPossibilities, 0);
		assertEquals(maxRange, cGenerator.cRangeMax, cErrorMargin);
		assertEquals(minRange, cGenerator.cRangeMin, cErrorMargin);
	}
	
	/**
	 * Tests if a instance of a Random object is made in the reward generator.
	 */
	@Test
	public void constructorTest() {
		assertTrue(cGenerator.cRNG != null);
	}
	
	/**
	 * Tests if randomDouble always generates a double <= maxRange.
	 */
	@Test
	public void randomDoubleTest1() {
		final int randomPossibilities = 400000000,
				returnRandom = 400000000;
		
		cGenerator.cRNG = cRandom;
		Mockito.when(cRandom.nextInt(randomPossibilities)).thenReturn(returnRandom);
		assertEquals(cGenerator.cRangeMax, cGenerator.randomDouble(), cErrorMargin);
	}
	
	/**
	 * Tests if randomDouble always generates a double >= minRange.
	 */
	@Test
	public void randomDoubleTest2() {
		final int randomPossibilities = 400000000,
				returnRandom = 0;
		
		cGenerator.cRNG = cRandom;
		Mockito.when(cRandom.nextInt(randomPossibilities)).thenReturn(returnRandom);
		assertEquals(cGenerator.cRangeMin, cGenerator.randomDouble(), cErrorMargin);
	}
	
	/**
	 * Tests if the input of rewardFunction gets transformed by the specified function
	 * in rewardFunction (In this case f(x) = x^4).
	 */
	@Test
	public void rewardFunctionTest() {
		final int input1 = 6,
				result1 = 1296;
		final double input2 = 12.4,
				result2 = 23642.1376;
		assertEquals(result1, cGenerator.rewardFunction(input1), 0);
		assertEquals(result2, cGenerator.rewardFunction(input2), cErrorMargin);
	}
	
	/**
	 * Tests if the input of inverseRewardFunction gets transformed by the specified function
	 * in inverseRewardFunction (In this case f(x) = x^(1/4)).
	 */
	@Test
	public void inverseRewardFunctionTest() {
		final int input1 = 1296,
				result1 = 6;
		final double input2 = 23642.1376,
				result2 = 12.4;
		assertEquals(result1, cGenerator.inverseRewardFunction(input1), 0);
		assertEquals(result2, cGenerator.inverseRewardFunction(input2), cErrorMargin);
	}
	
	/**
	 * Tests if the generateRewardColour method always generates a wavelength <= 780.
	 */
	@Test
	public void generateRewardColourTest1() {
		final int randomPossibilities = 400000000,
				returnRandom = 0,
				wavelength = 780;
		cGenerator.cRNG = cRandom;
		Mockito.when(cRandom.nextInt(randomPossibilities)).thenReturn(returnRandom);
		assertEquals(wavelength, cGenerator.generateRewardColour());
	}
	
	/**
	 * Tests if the generateRewardColour method always generates a wavelength >= 380.
	 */
	@Test
	public void generateRewardColourTest2() {
		final int randomPossibilities = 400000000,
				returnRandom = 400000000,
				wavelength = 380;
		cGenerator.cRNG = cRandom;
		Mockito.when(cRandom.nextInt(randomPossibilities)).thenReturn(returnRandom);
		assertEquals(wavelength, cGenerator.generateRewardColour());
	}

}

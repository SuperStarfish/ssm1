package cg.group4.data_structures.collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests to ensure proper behaviour of the RewardGenerator.
 *
 * @author Jean de Leeuw
 */
public class RewardGeneratorTest {

    /**
     * Error margin allowed in the tests.
     */
    protected final double cErrorMargin = 0.000001;
    /**
     * Instance of RewardGenerator to test.
     */
    protected RewardGenerator cGenerator;

    /**
     * Constructs the mock (but does not assign it yet, this happens in the tests themselves)
     * and constructs the reward generator to test.
     */
    @Before
    public void setUp() {
        cGenerator = new RewardGenerator("");
    }

    /**
     * Destroys the mock and reward generator so every test works with clean objects.
     */
    @After
    public void tearDown() {
        cGenerator = null;
    }

    /**
     * Tests if a instance of a Random object is made in the reward generator.
     */
    @Test
    public void constructorTest() {
        assertTrue(cGenerator.cRNG != null);
        assertTrue(cGenerator.cCollectibleFactory != null);
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

}

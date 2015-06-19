package cg.group4.data_structures.collection;

import cg.group4.data_structures.collection.collectibles.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests to ensure proper behaviour of the RewardGenerator.
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
        cGenerator = new RewardGenerator("Group4");
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
        assertEquals("Group4", cGenerator.cOwnerId);
    }

    @Test
    public void generateOneCollectibleTest1() {
        Random rngMock = Mockito.mock(Random.class);
        Mockito.when(rngMock.nextInt(Mockito.anyInt())).thenReturn(0);
        Mockito.when(rngMock.nextFloat()).thenReturn(0.5f);

        cGenerator.cRNG = rngMock;

        float hue = (float) cGenerator.rewardFunction(0.5f);

        Collectible generatedFish = cGenerator.generateOneCollectible();
        assertEquals(new FishA(hue, "Group4"), generatedFish);
        Mockito.verify(rngMock, Mockito.times(1)).nextInt(Mockito.anyInt());
        Mockito.verify(rngMock, Mockito.times(1)).nextFloat();
    }

    @Test
    public void getMostRareTest1() {
        FishA fA = new FishA(1f, "ABC");
        FishB fB = new FishB(1f, "ABC");

        Collectible mostRare = cGenerator.getMostRare(fA, fB);
        assertEquals(fB, mostRare);
    }

    @Test
    public void getMostRareTest2() {
        FishC fC = new FishC(1f, "ABC");
        FishB fB = new FishB(1f, "ABC");

        Collectible mostRare = cGenerator.getMostRare(fC, fB);
        assertEquals(fC, mostRare);
    }

    @Test
    public void generateOneCollectibleTest2() {
        Random rngMock = Mockito.mock(Random.class);
        Mockito.when(rngMock.nextInt(Mockito.anyInt())).thenReturn(2);
        Mockito.when(rngMock.nextFloat()).thenReturn(1f);

        cGenerator.cRNG = rngMock;

        float hue = (float) cGenerator.rewardFunction(1f);

        Collectible generatedFish = cGenerator.generateOneCollectible();
        assertEquals(new FishC(hue, "Group4"), generatedFish);
        Mockito.verify(rngMock, Mockito.times(1)).nextInt(Mockito.anyInt());
        Mockito.verify(rngMock, Mockito.times(1)).nextFloat();
    }

    @Test
    public void generateCollectibleTest1() {
        RewardGenerator genSpy = Mockito.spy(cGenerator);
        final int eventScore = 20;

        genSpy.generateCollectible(eventScore);

        Mockito.verify(genSpy, Mockito.times(eventScore)).generateOneCollectible();
        Mockito.verify(genSpy, Mockito.times(eventScore)).getMostRare(
                Mockito.any(Collectible.class),
                Mockito.any(Collectible.class));
    }

    @Test
    public void generateCollectibleTest2() {
        FishA fishA = new FishA(1f, "Group4");
        CollectibleFactory factorySpy = Mockito.spy(CollectibleFactory.class);
        Mockito.when(factorySpy.generateCollectible(Mockito.anyString(), Mockito.anyFloat(), Mockito.eq("Group4")))
                .thenReturn(fishA);

        cGenerator.cCollectibleFactory = factorySpy;
        Collectible generatedCollectible = cGenerator.generateCollectible(1);
        assertEquals(fishA, generatedCollectible);
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

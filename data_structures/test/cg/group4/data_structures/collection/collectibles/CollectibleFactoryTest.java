package cg.group4.data_structures.collection.collectibles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CollectibleFactoryTest {

    protected final float cHue = 0.8f;
    protected final String cOwner = "Group4";
    protected final int cAmount = 5;
    /**
     * CollectibleFactory to test.
     */
    protected CollectibleFactory cFactory;
    protected Date cDate;

    @Before
    public void setUp() {
        cFactory = new CollectibleFactory();
        cDate = new Date();
    }

    @After
    public void tearDown() {
        cFactory = null;
        cDate = null;
    }

    @Test
    public void generateCollectibleTest1() {
        FishA fA = new FishA(cHue, cOwner);
        Collectible generatedCol = cFactory.generateCollectible("FishA", cHue, cOwner);

        assertEquals(fA, generatedCol);
    }

    @Test
    public void generateCollectibleTest2() {
        FishB fB = new FishB(cHue, cOwner);
        Collectible generatedCol = cFactory.generateCollectible("FishB", cHue, cOwner);

        assertEquals(fB, generatedCol);
    }

    @Test
    public void generateCollectibleTest3() {
        FishC fC = new FishC(cHue, cOwner);
        Collectible generatedCol = cFactory.generateCollectible("FishC", cHue, cOwner);

        assertEquals(fC, generatedCol);
    }

    @Test
    public void generateCollectibleTest4() {
        Collectible generatedCol = cFactory.generateCollectible("", cHue, cOwner);

        assertTrue(generatedCol == null);
    }

    @Test
    public void generateCollectibleTest5() {
        FishA fA = new FishA(cHue, cAmount, cDate, cOwner);
        Collectible generatedCol = cFactory.generateCollectible("FishA", cHue, cAmount, cDate, cOwner);

        assertEquals(fA, generatedCol);
    }

    @Test
    public void generateCollectibleTest6() {
        FishB fB = new FishB(cHue, cAmount, cDate, cOwner);
        Collectible generatedCol = cFactory.generateCollectible("FishB", cHue, cAmount, cDate, cOwner);

        assertEquals(fB, generatedCol);
    }

    @Test
    public void generateCollectibleTest7() {
        FishC fC = new FishC(cHue, cAmount, cDate, cOwner);
        Collectible generatedCol = cFactory.generateCollectible("FishC", cHue, cAmount, cDate, cOwner);

        assertEquals(fC, generatedCol);
    }

    @Test
    public void generateCollectibleTest8() {
        Collectible generatedCol = cFactory.generateCollectible("", cHue, cAmount, cDate, cOwner);

        assertTrue(generatedCol == null);
    }

}

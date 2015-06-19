package cg.group4.data_structures.collection.collectibles.collectible_comparators;

import cg.group4.data_structures.collection.collectibles.FishA;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RarityComparatorTest {

    /**
     * RarityComparator to test.
     */
    protected RarityComparator cComparator;

    @Before
    public void setUp() {
        cComparator = new RarityComparator();
    }

    @After
    public void tearDown() {
        cComparator = null;
    }

    @Test
    public void compareTest1() {
        FishA fA1 = new FishA(1f, "A");
        FishA fA2 = new FishA(0.5f, "A");

        int compareResult = cComparator.compare(fA1, fA2);
        assertTrue(compareResult == -1);
    }

    @Test
    public void compareTest2() {
        FishA fA1 = new FishA(0.3f, "A");
        FishA fA2 = new FishA(0.3f, "A");

        int compareResult = cComparator.compare(fA1, fA2);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareTest3() {
        FishA fA1 = new FishA(0.3f, "A");
        FishA fA2 = new FishA(0.7f, "A");

        int compareResult = cComparator.compare(fA1, fA2);
        assertTrue(compareResult == 1);
    }

    @Test
    public void toStringTest() {
        final String expected = "Rarity";
        assertEquals(expected, cComparator.toString());
    }

}

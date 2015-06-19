package cg.group4.data_structures.collection.collectibles.collectible_comparators;

import cg.group4.data_structures.collection.collectibles.FishA;
import cg.group4.data_structures.collection.collectibles.FishB;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HueComparatorTest {

    /**
     * HueComparator to test.
     */
    protected HueComparator cComparator;

    @Before
    public void setUp() {
        cComparator = new HueComparator();
    }

    @After
    public void tearDown() {
        cComparator = null;
    }

    @Test
    public void compareTest1() {
        FishA fA = new FishA(1f, "A");
        FishB fB = new FishB(0.5f, "B");

        int compareResult = cComparator.compare(fA, fB);
        assertTrue(compareResult == -1);
    }

    @Test
    public void compareTest2() {
        FishA fA = new FishA(0.3f, "A");
        FishB fB = new FishB(0.3f, "B");

        int compareResult = cComparator.compare(fA, fB);
        assertTrue(compareResult == 0);
    }

    @Test
    public void compareTest3() {
        FishA fA = new FishA(0.3f, "A");
        FishB fB = new FishB(0.7f, "B");

        int compareResult = cComparator.compare(fA, fB);
        assertTrue(compareResult == 1);
    }

    @Test
    public void toStringTest() {
        final String expected = "Hue";
        assertEquals(expected, cComparator.toString());
    }

}

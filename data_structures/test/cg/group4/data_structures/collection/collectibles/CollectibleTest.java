package cg.group4.data_structures.collection.collectibles;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class CollectibleTest {

    /**
     * Collectible to test.
     */
    protected Collectible cFishA;

    @Before
    public void setUp() {
        cFishA = new FishA(1f, "A");
    }

    @After
    public void tearDown() {
        cFishA = null;
    }

    @Test
    public void constructor1Test() {
        final float errorMargin = 0f;
        final float timeErrorMargin = 60000f; //One minute
        assertEquals(1f, cFishA.getHue(), errorMargin);
        assertTrue(new Date().getTime() - cFishA.getDate().getTime() <= timeErrorMargin);
        assertEquals(1, cFishA.getAmount());
        assertEquals("A", cFishA.getOwnerId());
    }

    @Test
    public void constructor2Test() {
        final float errorMargin = 0f;
        final float hue = 0.3f;
        final int amount = 10;
        final Date date = new Date();
        final String ownerId = "B";

        FishB fishB = new FishB(hue, amount, date, ownerId);
        assertEquals(hue, fishB.getHue(), errorMargin);
        assertEquals(date, fishB.getDate());
        assertEquals(amount, fishB.getAmount());
        assertEquals(ownerId, fishB.getOwnerId());
    }

    @Test
    public void getDateAsStringTest() {
        final String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        assertEquals(dateFormat, cFishA.getDateAsString());
    }

    @Test
    public void incrementAmountTest() {
        assertTrue(cFishA.getAmount() == 1);
        cFishA.incrementAmount();
        assertTrue(cFishA.getAmount() == 2);
    }

    @Test
    public void toStringTest1() {
        final String collectibleString = "Collectible<"
                + "hue = " + cFishA.getHue() + ", "
                + "amount = " + cFishA.getAmount() + ", "
                + "form rarity = " + cFishA.getFormRarity()
                + ">";
        assertEquals(collectibleString, cFishA.toString());
    }

    @Test
    public void toStringTest2() {
        final FishB fishB = new FishB(0.3f, "B");
        final String collectibleString = "Collectible<"
                + "hue = " + fishB.getHue() + ", "
                + "amount = " + fishB.getAmount() + ", "
                + "form rarity = " + fishB.getFormRarity()
                + ">";
        assertEquals(collectibleString, fishB.toString());
    }

    @Test
    public void equalsTest1() {
        FishA fA1 = new FishA(1f, "A");
        FishA fA2 = new FishA(0.3f, "A");
        FishA fA3 = new FishA(1f, "B");

        assertEquals(fA1, cFishA);
        assertFalse(fA2.equals(cFishA));
        assertFalse(fA3.equals(cFishA));
    }

    @Test
    public void equalsTest2() {
        FishB fB = new FishB(1f, "A");
        FishC fC = new FishC(1f, "A");

        assertFalse(fB.equals(cFishA));
        assertFalse(fB.equals(fC));
        assertFalse(fC.equals(cFishA));
    }

    @Test
    public void equalsTest3() {
        FishA fA = null;
        Collectible fCol = cFishA;
        assertFalse(cFishA.equals(fA));
        assertEquals(fCol, cFishA);
    }
}

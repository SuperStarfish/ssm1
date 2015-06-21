package cg.group4.data_structures.collection;

import cg.group4.data_structures.collection.collectibles.Collectible;
import cg.group4.data_structures.collection.collectibles.FishA;
import cg.group4.data_structures.collection.collectibles.FishB;
import cg.group4.data_structures.collection.collectibles.FishC;
import cg.group4.data_structures.collection.collectibles.collectible_comparators.RarityComparator;
import cg.group4.data_structures.subscribe.Subject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CollectionTest {

    protected Collection cCollection;

    @Before
    public void setUp() {
        cCollection = new Collection("ColA");
    }

    @After
    public void tearDown() {
        cCollection = null;
    }

    @Test
    public void constructorTest() {
        assertEquals("ColA", cCollection.cId);
        assertTrue(cCollection.cChangeAddSubject != null);
    }

    @Test
    public void toStringTest() {
        FishA fA = new FishA(0.3f, "A");
        FishB fB = new FishB(0.7f, "B");
        cCollection.add(fA);
        cCollection.add(fB);
        final String collectionString = "Collection<"
                + fA.toString() + ", "
                + fB.toString()
                + ">";

        assertEquals(collectionString, cCollection.toString());
    }

    @Test
    public void sortTest() {
        RarityComparator sortRarity = new RarityComparator();

        cCollection.add(new FishA(1f, "A"));
        cCollection.add(new FishB(1f, "B"));
        cCollection.add(new FishC(1f, "C"));

        ArrayList<Collectible> sortedList = cCollection.sort(sortRarity);
        double prevRarity = Double.MAX_VALUE;
        for (Collectible c : sortedList) {
            assertTrue(c.getRarity() <= prevRarity);
            prevRarity = c.getRarity();
        }
    }

    @Test
    public void addAllTest() {
        Subject addMock = Mockito.mock(Subject.class);
        cCollection.cChangeAddSubject = addMock;

        Collection toAdd = new Collection("ColS");
        FishB fB = new FishB(1f, "B");
        FishC fC = new FishC(1f, "C");

        toAdd.add(fB);
        toAdd.add(fC);

        boolean succes = cCollection.addAll(toAdd);
        assertTrue(succes);
        assertTrue(cCollection.contains(fB));
        assertTrue(cCollection.contains(fC));
        Mockito.verify(addMock, Mockito.times(1)).update(toAdd);
    }

    @Test
    public void resetCollectionTest() {
        assertTrue(cCollection.isEmpty());

        FishA fA = new FishA(1f, "A");
        FishB fB = new FishB(1f, "B");
        cCollection.add(fA);
        cCollection.add(fB);

        assertFalse(cCollection.isEmpty());
        assertTrue(cCollection.contains(fA));
        assertTrue(cCollection.contains(fB));

        cCollection.resetCollection();
        assertTrue(cCollection.isEmpty());
        assertFalse(cCollection.contains(fA));
        assertFalse(cCollection.contains(fB));
    }
}

package cg.group4.data_structures.collection.collectibles;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FishATest {

    @Test
    public void getImagePathTest() {
        FishA fishA = new FishA(1f, "Group4");
        final String path = "images/FishA.png";
        assertEquals(path, fishA.getImagePath());
    }
}

package cg.group4.data_structures.collection.collectibles;

import static org.junit.Assert.*;

import org.junit.Test;

public class FishBTest {

	@Test
	public void getImagePathTest() {
		FishB fishB = new FishB(1f, "Group4");
		final String path = "images/FishB.png";
		assertEquals(path, fishB.getImagePath());
	}
}

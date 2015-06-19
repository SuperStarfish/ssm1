package cg.group4.data_structures.collection.collectibles;

import static org.junit.Assert.*;

import org.junit.Test;

public class FishCTest {

	@Test
	public void getImagePathTest() {
		FishC fishC = new FishC(1f, "Group4");
		final String path = "images/FishC.png";
		assertEquals(path, fishC.getImagePath());
	}

}

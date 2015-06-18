package cg.group4.data_structures.collection.collectibles;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FishATest {
	
	@Test
	public void getImagePathTest() {
		FishA fishA = new FishA(1f, "Group4");
		final String path = "images/FishA.png";
		assertEquals(path, fishA.getImagePath());
	}
}

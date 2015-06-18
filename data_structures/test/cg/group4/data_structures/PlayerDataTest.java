package cg.group4.data_structures;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cg.group4.data_structures.collection.Collection;

public class PlayerDataTest {
	
	/**
	 * PlayerData to test.
	 */
	protected PlayerData cData;

	@Before
	public void setUp() {
		cData = new PlayerData("Group4");
	}

	@After
	public void tearDown() {
		cData = null;
	}

	@Test
	public void constructorTest() {
		final String playerID = "Group4";
		assertEquals(playerID, cData.getId());
		assertEquals(new Collection(playerID), cData.getCollection());
	}

}

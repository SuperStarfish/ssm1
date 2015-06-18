package cg.group4.data_structures.groups;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import cg.group4.data_structures.collection.Collection;

public class GroupTest {
	
	/**
	 * Group to test.
	 */
	protected Group cGroup;

	@Before
	public void setUp() {
		cGroup = new Group("Group4");
	}

	@After
	public void tearDown() {
		cGroup = null;
	}

	@Test
	public void constructor1Test() {
		assertEquals(new Collection("Group4"), cGroup.cCollection);
		assertTrue(cGroup.cGroupData == null);
	}
	
	@Test
	public void constructor2Test() {
		GroupData groupdata = new GroupData();
		cGroup = new Group("Group4", groupdata);
		
		assertEquals(new Collection("Group4"), cGroup.cCollection);
		assertEquals(groupdata, cGroup.cGroupData);
	}

}

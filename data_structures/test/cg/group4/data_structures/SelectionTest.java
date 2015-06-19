package cg.group4.data_structures;

import cg.group4.data_structures.groups.GroupData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SelectionTest {

    /**
     * Selection to test.
     */
    protected Selection cSelection;

    /**
     * Groupdata mock to help test selection.
     */
    protected GroupData cDataMock;

    @Before
    public void setUp() {
        cDataMock = Mockito.mock(GroupData.class);
        Mockito.when(cDataMock.toString()).thenReturn("Group4");
        Mockito.when(cDataMock.getGroupId()).thenReturn("1");
        cSelection = new Selection(cDataMock);
    }

    @After
    public void tearDown() {
        cDataMock = null;
        cSelection = null;
    }

    @Test
    public void constructor1Test() {
        final String groupName = "Group4";
        final String groupIDString = "1";
        assertEquals(groupName, cSelection.cName);
        assertEquals(groupIDString, cSelection.cValue);
    }

    @Test
    public void constructor2Test() {
        final String playerName = "Roamin";
        PlayerData playerData = new PlayerData(playerName);

        cSelection = new Selection(playerData);
        assertNull(cSelection.cName);
        assertEquals(playerName, cSelection.cValue);
    }

    @Test
    public void toStringTest() {
        final String selectionName = "Group4";
        assertEquals(selectionName, cSelection.toString());
    }
}

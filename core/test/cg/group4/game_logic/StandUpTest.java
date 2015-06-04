package cg.group4.game_logic;

import cg.group4.GdxTestRunner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Observable;
import java.util.Observer;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Contains tests for the StandUp class.
 */
@RunWith(GdxTestRunner.class)
public class StandUpTest {

    /**
     * StandUp object to be tested.
     */
    protected StandUp cStandUp;

    /**
     * SetUp to run before each test.
     */
    @Before
    public void setUp() {
        cStandUp = StandUp.getInstance();
    }

    /**
     * Teardown to run after each test.
     */
    @After
    public void tearDown() {
        StandUp.reset();
        Preferences preferences = Gdx.app.getPreferences("TIMER");
        preferences.clear();
        preferences.flush();
    }

    /**
     * Tests the reset of the singleton..
     */
    @Test
    public void testReset() {
        StandUp.reset();
        assertNotEquals(cStandUp, StandUp.getInstance());
    }

    /**
     * Tests the retrieval of the StandUp instance.
     */
    @Test
    public void testGetInstance() {
        assertEquals(cStandUp, StandUp.getInstance());
    }

    /**
     * Tests the creation of a new stroll.
     */
    @Test
    public void testStartStroll() {
        assertNull(cStandUp.getStroll());
        Observer mockObserver = mock(Observer.class);
        cStandUp.getNewStrollSubject().addObserver(mockObserver);
        cStandUp.startStroll();
        verify(mockObserver, times(1)).update((Observable) any(), any());
        assertNotNull(cStandUp.getStroll());
    }

    /**
     * Tests the removal of the stroll.
     */
    @Test
    public void testEndStroll() {
        cStandUp.startStroll();

        assertNotNull(cStandUp.getStroll());
        cStandUp.endStroll(0);
        assertNull(cStandUp.getStroll());
    }

    /**
     * Tests the retrieval of the stroll object.
     */
    @Test
    public void testGetStroll() {
        assertEquals(cStandUp.cStroll, cStandUp.getStroll());
    }

    /**
     * Tests the update class, to update the game mechanics.
     */
    @Test
    public void testUpdate() {
        Observer mockObserver = mock(Observer.class);
        cStandUp.getUpdateSubject().addObserver(mockObserver);
        cStandUp.update();
        verify(mockObserver, times(1)).update((Observable) any(), any());
    }

    /**
     * Tests the retrieval of the UpdateSubject.
     */
    @Test
    public void testGetUpdateSubject() {
        assertEquals(cStandUp.cUpdateSubject, cStandUp.getUpdateSubject());
    }

    /**
     * Tests the retrieval of the StrollSubject.
     */
    @Test
    public void testGetNewStrollSubject() {
        assertEquals(cStandUp.cNewStrollSubject, cStandUp.getNewStrollSubject());
    }

    /**
     * Tests the retrieval of the SensorReader.
     */
    @Test
    public void testGetSensorReader() {
        assertEquals(cStandUp.cSensorReader, cStandUp.getSensorReader());
    }
}

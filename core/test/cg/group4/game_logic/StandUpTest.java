package cg.group4.game_logic;

import cg.group4.GdxTestRunner;
import cg.group4.util.timer.TimeKeeper;
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
     * TimeKeeper to handle the timers.
     */
    protected TimeKeeper cTimeKeeper;

    @Before
    public void setUp() {
        cStandUp = StandUp.getInstance();
        cTimeKeeper = TimeKeeper.getInstance();
        cTimeKeeper.init();
    }

    @After
    public void tearDown() {
        cStandUp = null;
        cTimeKeeper
        Preferences preferences = Gdx.app.getPreferences("TIMER");
        preferences.clear();
        preferences.flush();
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

    @Test
    public void testEndStroll() {

    }

    /**
     * Tests the retrieval of the stroll object.
     */
    @Test
    public void testGetStroll() {
        assertEquals(cStandUp.cStroll, cStandUp.getStroll());
    }

    @Test
    public void testUpdate() {

    }

    /**
     * Tests the retrieval of the UpdateSubject.
     */
    @Test
    public void testGetUpdateSubject() {
        assertEquals(cStandUp.cUpdateSubject, cStandUp.getUpdateSubject());
    }

    /**
     * Tests the retrieval of the newStrollSubject.
     */
    @Test
    public void testGetNewStrollSubject() {
        assertEquals(cStandUp.cNewStrollSubject, cStandUp.getNewStrollSubject());
    }

    @Test
    public void testGetSensorReader() {

    }
}

package cg.group4.util.timer;

import cg.group4.GdxTestRunner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Observable;
import java.util.Observer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GdxTestRunner.class)
public class TimerTest {
    Timer timer;
    Observer timerObserver;

    @Before
    public void setUp() {
        timer = new Timer("TEST", 5);
        timerObserver = mock(Observer.class);
    }


    @Test
    public void testGetName() {
        assertEquals("TEST", timer.getName());
    }

    @Test
    public void testEqualsTrue() {
        assertTrue(timer.equals(new Timer("TEST", 60, true)));
    }

    @Test
    public void testEqualsNull() {
        assertFalse(timer.equals(null));
    }

    @Test
    public void testEqualsNonTimerClass() {
        assertFalse(timer.equals("BLA"));
    }

    @Test
    public void testNotEquals() {
        assertFalse(timer.equals(new Timer("WRONG", 5)));
    }


    @Test
    public void testOnTickWhenFinished(){
        long timeStamp = System.currentTimeMillis();
        timer.cFinishTime = timeStamp - timer.cDuration;
        timer.tick(timeStamp);
        assertFalse(timer.cRunning);
    }

    @Test
    public void testTickWhenNotRunning(){
        timer.stop();
        timer.getTickSubject().addObserver(timerObserver);
        timer.tick(System.currentTimeMillis());
        verify(timerObserver, never()).update((Observable) any(), any());
    }

    @Test public void testSetFinishTimePersistent(){
        timer = new Timer("TEST", 60, true);
        timer = new Timer("TEST", 60, true);
        assertTrue(timer.cRunning);
    }

    @Test public void testSetFinishTimePersistentFinished(){
        timer = new Timer("TEST", 60, true);
        timer.cPreferences.putLong(timer.cName, System.currentTimeMillis() - timer.cDuration);
        timer = new Timer("TEST", 60, true);
        assertFalse(timer.cRunning);
    }

    @Test public void testResetFinishTime(){
        Timer timer = new Timer("BLABLA", 60, true);
        long time = timer.cPreferences.getLong(timer.cName);
        timer.tick(System.currentTimeMillis()+1000);
        timer.resetFinishTime();
        assertTrue(timer.cPreferences.getLong(timer.cName) > time);
    }


    @Test
    public void testTimerEnumINTERVAL() {
        assertEquals(60 * 60, Timer.Global.INTERVAL.getDuration());
    }

    @Test
    public void testTimerEnumSTROLL() {
        assertEquals(5 * 60, Timer.Global.STROLL.getDuration());
    }

    @After
    public void tearDown(){
        Preferences preferences = Gdx.app.getPreferences("TIMER");
        preferences.clear();
        preferences.flush();
    }

}

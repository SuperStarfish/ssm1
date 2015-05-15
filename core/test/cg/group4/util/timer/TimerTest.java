package cg.group4.util.timer;

import cg.group4.GdxTestRunner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class TimerTest {
    Timer timer;
    TimerTask timerTask;

    @Before
    public void setUp() {
        timer = new Timer("TEST", 5);
        timerTask = mock(TimerTask.class);
    }

    @Test
    public void testAddTimerTask() {
        timer.subscribe(timerTask);
        assertEquals(1, timer.cTimerTasks.size());
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
    public void testSubscribeOnRunningTimer() {
        timer.subscribe(timerTask);
        verify(timerTask, times(1)).onStart();
    }

    @Test
    public void testSubscribeOnTimerTick() {
        timer.subscribe(timerTask);
        long timeStamp = System.currentTimeMillis();
        timer.tick(timeStamp);
        verify(timerTask, times(1)).onTick((int) (timer.cFinishTime - timeStamp) / 1000);
    }

    @Test
    public void testOnTickWhenFinished(){
        timer.subscribe(timerTask);
        long timeStamp = System.currentTimeMillis();
        timer.cFinishTime = timeStamp - timer.cDuration;
        timer.tick(timeStamp);
        assertFalse(timer.cRunning);
    }

    @Test
    public void testTickWhenNotRunning(){
        timer.stop();
        timer.subscribe(timerTask);
        timer.tick(System.currentTimeMillis());
        verify(timerTask, never()).onTick(Mockito.anyInt());
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
        try{
            Timer timer = new Timer("BLABLA", 60, true);
            long time = timer.cPreferences.getLong(timer.cName);
            Thread.sleep(1000);
            timer.resetFinishTime();
            assertTrue(timer.cPreferences.getLong(timer.cName) > time);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Test
    public void testSubscribeOnNotRunningTimer() {
        timer.cPreferences = mock(Preferences.class);
        timer.stop();
        timer.subscribe(timerTask);
        verify(timerTask, times(1)).onStop();
    }

    @Ignore
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

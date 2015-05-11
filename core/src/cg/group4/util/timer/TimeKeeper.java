package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class TimeKeeper {
    Timer c_intervalTimer, c_strollTimer;

    private final int INTERVAL_TIME = 60 * 60,
                        STROLL_TIME = 5 * 60;

    Preferences c_preferences;

    public TimeKeeper(){
        c_preferences = Gdx.app.getPreferences("TIMERS");
        c_intervalTimer = new Timer();
        c_strollTimer = new Timer();
    }

    public void checkTimers(){
        long setOn = c_preferences.getLong("INTERVAL_TIMER");

        c_preferences.putLong("EVENT_TIMER", System.currentTimeMillis() + 1000);
        c_preferences.flush();
    }

    public Timer getIntervalTimer(){
        return c_intervalTimer;
    }

    public Timer getStrollTimer(){
        return c_strollTimer;
    }
}

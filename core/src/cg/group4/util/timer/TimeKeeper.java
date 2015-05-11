package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashSet;
import java.util.Set;

public class TimeKeeper {
    public static final TimeKeeper instance = new TimeKeeper();
    public static final String TAG = TimeKeeper.class.getSimpleName();

    Set<Timer> c_timers;

    private TimeKeeper(){
    }

    public void init(){
        Gdx.app.debug(TAG, "Created a new TimeKeeper!");
        c_timers = new HashSet<Timer>();
        instance.addTimer(new Timer("INTERVAL", 60 * 60, true));
        instance.addTimer(new Timer("STROLL", 5 * 60, true));
    }

    public void update(){
        for(Timer timer : c_timers){
            timer.update();
        }
    }

    public void addTimer(Timer timer){
        if(c_timers.add(timer)) {
            Gdx.app.debug(TAG, "Added Timer '" + timer.getName() + "'.");
        }
    }

    public Timer getTimer(String name){
        for(Timer timer : c_timers){
            if(timer.getName().equals(name)){
                return timer;
            }
        }
        return null;
    }
}

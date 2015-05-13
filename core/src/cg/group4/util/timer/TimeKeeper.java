package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class TimeKeeper {
    public static final String TAG = TimeKeeper.class.getSimpleName();
    private static final TimeKeeper instance = new TimeKeeper();
    public static TimeKeeper getInstance() {
        return instance;
    }


    protected Set<Timer> c_timers;
    protected long c_previousTick;

    protected TimeKeeper(){
        c_timers = new HashSet<Timer>();
        c_previousTick = System.currentTimeMillis();
        Gdx.app.debug(TimeKeeper.TAG, "Created a new TimeKeeper!");
    }

    public void init(){
        for(Timer.Global timer : Timer.Global.values()){
            new Timer(timer.name(), timer.getDuration(), true);
        }
    }

    public void update(){
        long timeStamp = System.currentTimeMillis();
        if(timeStamp - c_previousTick > 1000) {
            for (Timer timer : c_timers) {
                timer.tick(timeStamp);
            }
            c_previousTick += 1000;
        }
    }


    protected boolean containsTimer(String name){
        for(Timer timer : c_timers){
            if(timer.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    void addTimer(Timer timer){
        if(c_timers.add(timer)){
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

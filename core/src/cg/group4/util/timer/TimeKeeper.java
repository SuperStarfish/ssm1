package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;

import java.util.LinkedHashSet;

public class TimeKeeper {
    public static final String TAG = TimeKeeper.class.getSimpleName();
    private static final TimeKeeper instance = new TimeKeeper();
    public static TimeKeeper getInstance() {
        return instance;
    }


    LinkedHashSet<Timer> c_timers;
    private long c_previousTick;

    private TimeKeeper(){
        System.out.println(TimeKeeper.class.getSimpleName());
        Gdx.app.debug(TimeKeeper.TAG, "Created a new TimeKeeper!");
        c_timers = new LinkedHashSet<Timer>();
        for(Timer.Global timer : Timer.Global.values()){
            addTimer(new Timer(timer.name(), timer.getDuration(), true));
        }
        c_previousTick = System.currentTimeMillis();
    }

    public void update(){
        long timeStamp = System.currentTimeMillis();
        if(timeStamp - c_previousTick > 1000) {
            for (Timer timer : c_timers) {
                timer.tick(timeStamp);
            }
            c_previousTick = timeStamp;
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

    public void addTimer(Timer timer){
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

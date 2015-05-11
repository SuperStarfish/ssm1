package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashSet;
import java.util.Set;

public class Timer{
    public static final String TAG = Timer.class.getSimpleName();
    protected String c_name;
    protected Set<TimerTask> c_timerTask;
    long c_finished;

    public Timer(String name, int duration){
        createPersistentTimer(name, duration, false);
    }

    public Timer(String name, int duration, boolean persistent){
        createPersistentTimer(name, duration, persistent);
    }

    protected void createPersistentTimer(String name, int duration, boolean persistent){
        c_name = name;
        c_timerTask = new HashSet<TimerTask>();
        Gdx.app.debug(TAG, "Creating a new " + name + " Timer.");

        Preferences prefs = Gdx.app.getPreferences("TIMER");

        long time = System.currentTimeMillis();
        if(persistent && prefs.contains(name)){
            c_finished = prefs.getLong(name);
            Gdx.app.debug(TAG, "Using timestamp found in preferences: " +
                    ((c_finished - time) / 1000) + "seconds from now.");
        } else{
            c_finished = time + duration * 1000;
            Gdx.app.debug(TAG, "Timer created from scratch with persistence: " + persistent);
            if(persistent){
                prefs.putLong(name, c_finished);
                prefs.flush();
            }
        }
    }

    public String getName(){
        return c_name;
    }

    public void update(){
        if(System.currentTimeMillis() > c_finished){
            Gdx.app.debug(c_name + TAG, "finished");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(!(obj instanceof Timer)) return false;
        return c_name.equals(((Timer) obj).getName());
    }

    public void subscribe(TimerTask task) {
        c_timerTask.add(task);
    }
}
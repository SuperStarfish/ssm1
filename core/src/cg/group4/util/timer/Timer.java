package cg.group4.util.timer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.util.HashSet;
import java.util.Set;

public class Timer{
    public static final String TAG = Timer.class.getSimpleName();
    protected String c_name;
    protected Set<TimerTask> c_timerTasks;
    protected int c_duration;
    protected long c_finishTime;
    protected Preferences c_preferences;
    protected boolean c_persistent;
    protected boolean c_running;


    public Timer(String name, int duration){
        init(name, duration, false);
    }

    public Timer(String name, int duration, boolean persistent){
        init(name,duration,persistent);
    }

    protected void init(String name, int duration, boolean persistent){
        c_name = name;
        c_duration = duration;
        c_timerTasks = new HashSet<TimerTask>();
        c_persistent = persistent;
        c_preferences = Gdx.app.getPreferences("TIMER");
        setFinishTime();
    }

    public void setFinishTime(){
        if(c_preferences.contains(c_name)){
            c_finishTime = c_preferences.getLong(c_name);
            System.out.println((c_finishTime-System.currentTimeMillis())/1000);
            if(System.currentTimeMillis()>c_finishTime){
                stop();
            } else {
                c_running = true;
            }
        }
        else {
            reset();
        }
    }

    public String getName(){
        return c_name;
    }

    public void tick(long timeStamp){
        if(c_running){
            if(timeStamp > c_finishTime){
                c_running = false;
                notifyStop();
            } else{
                notifyTick((int) (c_finishTime - timeStamp)/1000);
            }
        }
    }

    protected void notifyTick(int remainingTime){
        for(TimerTask task : c_timerTasks){
            task.onTick(remainingTime);
        }
    }

    protected void notifyStop(){
        for(TimerTask task : c_timerTasks){
            task.onStop();
        }
    }

    protected void notifyStart(){
        for(TimerTask task : c_timerTasks){
            task.onStart();
        }
    }

    public void stop(){
        c_preferences.putLong(c_name, System.currentTimeMillis());
        c_preferences.flush();
        c_running = false;
        notifyStop();
    }

    public void reset() {
        resetFinishTime();
        Gdx.app.debug(TAG, "Set " + getName() + "-Timer to finish " + ((c_finishTime - System.currentTimeMillis()) / 1000) + " seconds from now.");
        c_running = true;
        notifyStart();
    }

    protected void resetFinishTime(){
        c_finishTime = System.currentTimeMillis() + c_duration * 1000;
        if(c_persistent){
            c_preferences.putLong(c_name, c_finishTime);
            c_preferences.flush();
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj == null || !(obj instanceof Timer)) && c_name.equals(((Timer) obj).getName());
    }

    @Override
    public int hashCode(){
        return c_name.hashCode();
    }

    public void subscribe(TimerTask task) {
        c_timerTasks.add(task);
        task.setTimer(this);
    }

    public enum Name{
        INTERVAL(60 * 60), STROLL(5 * 60);

        private int e_duration;

        Name(int duration){
            e_duration = duration;
        }

        public int getDuration(){
            return e_duration;
        }
    }
}
package cg.group4.util.timer;

public abstract class TimerTask {
    private Timer c_timer;

    public void setTimer(Timer timer){
        c_timer = timer;
    }
    public Timer getTimer(){
        return c_timer;
    }

    public abstract void onTick(int seconds);
    public abstract void onStart();
    public abstract void onStop();
}
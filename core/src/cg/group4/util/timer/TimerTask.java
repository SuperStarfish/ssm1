package cg.group4.util.timer;

public abstract class TimerTask {
    public abstract void onRemaining(int seconds);
    public abstract void onStart();
    public abstract void onStop();
}
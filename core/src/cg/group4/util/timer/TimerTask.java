package cg.group4.util.timer;

/**
 * 
 * @author Jurgen van Schagen
 * @author Benjamin Los
 */
public abstract class TimerTask {
    private Timer c_timer;
    
    
    public void setTimer(Timer timer){
        c_timer = timer;
    }
    
    /** Returns the timer it belongs too.
     * @return	The timer this task belongs too.
     */
    public Timer getTimer(){
        return c_timer;
    }
    
    /**
     * Abstract method for what a task should do on a certain amount of ticks.
     * @param seconds	What should it do after this amount of seconds?
     */
    public abstract void onTick(int seconds);
    
    /**
     * Abstract method for what a task should do on startup of the timer.
     * @param seconds	What should it do after this amount of seconds?
     */
    public abstract void onStart();
    public abstract void onStop();
}
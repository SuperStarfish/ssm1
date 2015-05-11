package cg.group4.util.timer;

import java.util.HashSet;
import java.util.Set;

public class Timer{
    protected Set<TimerTask> c_timerTask;

    public Timer(){
        c_timerTask = new HashSet<>();

    }

    public void subscribe(TimerTask task) {
        c_timerTask.add(task);
    }
}
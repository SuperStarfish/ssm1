package cg.group4.util.Timer;

public interface TimerListener {
    /**
     * This method is called whenever a Timer is finished. The parameter n determines which timer by the specified amount of minutes.
     * @param n Identify the length of the timer.
     */
    void onNotify(int n);
}

package cg.group4.game_logic.stroll.events.fishevent;

import cg.group4.util.timer.Timer;
import com.badlogic.gdx.math.Vector3;

import java.util.Observable;
import java.util.Observer;

/**
 * State for the FishingEvent where you have to wait for the fishes to bite.
 * @author Nick Cleintuar
 */
public class WaitState implements FishEventState {

    /**
     * Constant used to describe when you have moved too much.
     */
    protected static final float DELTA = 1.5f;

    /**
     * The observer for the timer, on stop it should switch the state of the event.
     */
    protected Observer cFishStopObserver;

    /**
     * The timer which keeps track for how long you hold still.
     */
    protected Timer cFishTimer;

    /**
     * The event this state belongs to.
     */
    protected FishingStrollEvent cEvent;

    /**
     * Constructor, creates a new timer for this state.
     * @param event The event this state belongs to.
     */
    public WaitState(final FishingStrollEvent event) {
        cFishTimer = new Timer("WAITFORFISH", 5);

        cEvent = event;

        cEvent.cLabel.setText("Wait for the fish to bite");

        cFishStopObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                cFishTimer.stop();
                cEvent.cState = new ReelInState(cEvent);
            }
        };

        cFishTimer.getStopSubject().addObserver(cFishStopObserver);

    }

    /**
     *
     * @param input
     */
    public final void processInput(final Vector3 input) {

        float pytho = (float) Math.sqrt((Math.pow(input.x, 2) + Math.pow(input.y, 2) + Math.pow(input.z, 2)));
        if (pytho > DELTA) {
            cFishTimer.reset();
        }
    }
}

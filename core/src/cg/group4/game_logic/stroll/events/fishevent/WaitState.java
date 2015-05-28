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
     *
     */
    protected static final int TIME = 7;

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
        cFishTimer = new Timer("WAITFORFISH", TIME);

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
     * Method that processes the input to match the movement of waiting for the fish to bite, so holding still.
     * @param input Vector containing the acceleration in the x,y,z directions respectively.
     */
    public final void processInput(final Vector3 input) {

        float pythagorean = (float) Math.sqrt((Math.pow(input.x, 2) + Math.pow(input.y, 2) + Math.pow(input.z, 2)));
        if (pythagorean > DELTA) {
            cFishTimer.reset();
        }
    }
}

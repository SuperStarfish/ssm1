package cg.group4.game_logic.stroll.events.fishevent;

import com.badlogic.gdx.math.Vector3;

/**
 *
 */
public class CastForwardState implements FishEventState {

    /**
     * Constant describing when it is honest movement.
     */
    protected static final float DELTA = 3.5f;

    /**
     * Pointer to the event this is a state of.
     */
    protected FishingStrollEvent cEvent;

    /**
     * Constructor of the state, connects the state to the event.
     * @param event The event this state belongs to
     */
    public CastForwardState(final FishingStrollEvent event) {
        cEvent = event;
        cEvent.cLabel.setText("Throw your hook!");
    }

    /**
     * Method that processes the input to match the movement of casting your line into the water.
     * @param input Vector containing the acceleration in the x,y,z directions respectively.
     */
    public final void processInput(final Vector3 input) {

        if (input.z > DELTA) {
            cEvent.cState = new WaitState(cEvent);
        }
    }
}

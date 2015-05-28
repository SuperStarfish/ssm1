package cg.group4.game_logic.stroll.events.fishevent;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Nick on 27-5-2015.
 */
public class CastForwardState implements FishEventState {

    /**
     * Constant describing when it is honest movement.
     */
    protected static final float DELTA = 2.0f;

    /**
     * Pointer to the event this is a state of.
     */
    protected FishingStrollEvent cEvent;

    protected boolean forward;

    /**
     * Constructor of the state, connects the state to the event.
     * @param event The event this state belongs to
     */
    public CastForwardState(final FishingStrollEvent event) {
        cEvent = event;
        cEvent.cLabel.setText("Throw it!");
    }

    /**
     * Method that processes the input to match the movement of
     * @param input Vector containing the acceleration in the x,y,z directions respectively.
     */
    public final void processInput(final Vector3 input) {
        float movementxy = (float) Math.sqrt((Math.pow(input.x, 2) + Math.pow(input.y, 2)));
        float movementzy = (float) Math.sqrt((Math.pow(input.z, 2) + Math.pow(input.y, 2)));

        if (!forward) {
            if (input.y > 0) {
                if (movementxy > DELTA || movementzy > DELTA) {
                    forward = true;
                }
            }
        } else {
            if (input.y < 0) {
                if (movementxy > DELTA || movementzy > DELTA) {
                    cEvent.cDelayInputTimer.reset();
                    cEvent.cState = new WaitState(cEvent);
                }
            }
        }
    }
}

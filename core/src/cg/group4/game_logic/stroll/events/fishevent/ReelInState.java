package cg.group4.game_logic.stroll.events.fishevent;

import com.badlogic.gdx.math.Vector3;

/**
 * State for the fishing event, resembles pulling the fish in after it has bitten on the hook.
 */
public class ReelInState implements FishEventState {

    /**
     * Constant describing when the input should be accepted.
     */
    protected static final float DELTA = -2.5f;

    /**
     * Pointer to the event this is a state of.
     */
    protected FishingStrollEvent cEvent;

    /**
     * Constructor, creates this state.
     * @param event The event this state belongs to.
     */
    public ReelInState(final FishingStrollEvent event) {
        cEvent = event;
        cEvent.cLabel.setText("They have bitten! Pull your fish out of the water!");
    }

    /**
     * Method that processes the input to match the movement of pulling your fishing pole out of the water.
     * @param input Vector containing the acceleration in the x,y,z directions respectively.
     */
    public final void processInput(final Vector3 input) {
        if (input.z < DELTA) {
            cEvent.cCompletedTaskSound.play();
            cEvent.eventCompleted();
        }
    }
}

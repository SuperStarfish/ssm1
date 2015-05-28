package cg.group4.game_logic.stroll.events.fishevent;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Nick on 27-5-2015.
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
     *
     * @param event
     */
    public ReelInState(final FishingStrollEvent event){
        cEvent = event;
    }

    public final void processInput(final Vector3 input) {
        if (input.z < DELTA) {
            cEvent.eventCompleted();
        }
    }
}

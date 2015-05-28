package cg.group4.game_logic.stroll.events.fishevent;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Nick on 27-5-2015.
 */
public class CastBackState implements FishEventState {

    protected static final float DELTA = 2.0f;

    protected boolean behind;

    protected FishingStrollEvent cEvent;

    public CastBackState(FishingStrollEvent event) {
        cEvent = event;
        cEvent.cLabel.setText("Hold the screen towards you and cast your line back!");
    }

    public final void processInput(final Vector3 input) {
        float movementxy = (float) Math.sqrt((Math.pow(input.x, 2) + Math.pow(input.y, 2)));
        float movementzy = (float) Math.sqrt((Math.pow(input.z, 2) + Math.pow(input.y, 2)));

        if (!behind) {
            if (input.y > 0) {
                if (movementxy > DELTA || movementzy > DELTA) {
                    behind = true;
                }
            }
        } else {
            if (input.y < 0) {
                if (movementxy > DELTA || movementzy > DELTA) {
                    cEvent.cDelayInputTimer.reset();
                    cEvent.cState = new CastForwardState(cEvent);
                }
            }
        }
    }
}

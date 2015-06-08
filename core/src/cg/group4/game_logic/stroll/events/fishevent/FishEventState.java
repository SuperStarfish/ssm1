package cg.group4.game_logic.stroll.events.fishevent;

import com.badlogic.gdx.math.Vector3;

/**
 * Interface for each fishing state.
 */
public interface FishEventState {

    /**
     * Processes the input out of the accelerometer.
     *
     * @param input Vector containing the acceleration in the x,y,z direction.
     */
    void processInput(Vector3 input);
}

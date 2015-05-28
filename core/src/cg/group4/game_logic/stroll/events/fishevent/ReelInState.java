package cg.group4.game_logic.stroll.events.fishevent;

import com.badlogic.gdx.math.Vector3;

/**
 * Created by Nick on 27-5-2015.
 */
public class ReelInState implements FishEventState{

    protected static final float DELTA = -2.0f;

    protected FishingStrollEvent cEvent;

    public ReelInState(FishingStrollEvent event){
        cEvent = event;
    }

    public final void processInput(Vector3 input){
        if(input.z < DELTA){
            cEvent.eventCompleted();
        }
    }
}

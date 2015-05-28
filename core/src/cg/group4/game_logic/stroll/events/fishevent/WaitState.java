package cg.group4.game_logic.stroll.events.fishevent;

import com.badlogic.gdx.math.Vector3;

/**
 * State for the FishingEvent where you have to wait for the fishes to bite
 * @author Nick Cleintuar
 */
public class WaitState implements FishEventState {

    protected static final float DELTA = 2.5f;


    protected FishingStrollEvent cEvent;

    public WaitState(FishingStrollEvent event){
        cEvent = event;
    }
    public final void processInput(Vector3 input){

        float pytho = (float) Math.sqrt((Math.pow(input.x,2) + Math.pow(input.y,2) + Math.pow(input.z,2)));
    }
}

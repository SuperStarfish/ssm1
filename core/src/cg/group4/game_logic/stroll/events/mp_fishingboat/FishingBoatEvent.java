package cg.group4.game_logic.stroll.events.mp_fishingboat;

import cg.group4.data_structures.mp_fishingboat.FishingBoatData;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.game_logic.stroll.events.multiplayer.Boat;
import cg.group4.game_logic.stroll.events.multiplayer.Crane;
import cg.group4.game_logic.stroll.events.multiplayer.CraneHitbox;
import cg.group4.game_logic.stroll.events.multiplayer.SmallFish;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;

import java.util.ArrayList;
import java.util.Observable;

public class FishingBoatEvent extends StrollEvent {

    protected FishingBoatData fishingBoatData;

    protected Stack cBoatStack;
    protected Boat cBoat;
    protected Crane cCrane;
    protected CraneHitbox cCraneHitBox;
    protected ArrayList<SmallFish> fishList;



    @Override
    public int getReward() {
        return 0;
    }

    @Override
    protected void clearEvent() {

    }

    @Override
    public void start() {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}

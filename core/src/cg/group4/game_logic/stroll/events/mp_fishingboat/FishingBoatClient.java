package cg.group4.game_logic.stroll.events.mp_fishingboat;

import cg.group4.data_structures.mp_fishingboat.Coordinate;
import cg.group4.data_structures.mp_fishingboat.FishingBoatData;
import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.multiplayer_event.Host;
import cg.group4.game_logic.stroll.events.multiplayer_event.MessageHandler;
import cg.group4.game_logic.stroll.events.multiplayer_event.MultiplayerClient;
import cg.group4.util.sensor.Accelerometer;
import com.badlogic.gdx.math.Vector3;

import java.util.Observable;

public class FishingBoatClient extends FishingBoatEvent {

    protected MultiplayerClient cOtherClient;

    protected FishingBoatData fishingBoatData;

    protected Accelerometer cAccelmeter;

    public FishingBoatClient(Host host) {
        super();
        cOtherClient = (MultiplayerClient) host;
        cAccelmeter = new Accelerometer(StandUp.getInstance().getSensorReader());
        cAccelmeter.filterGravity(false);
        cAccelmeter.setNoiseThreshold(0.5f);
        cAccelmeter.setFilterPerAxis(true);

        fishingBoatData = new FishingBoatData();

        cOtherClient.receive(new MessageHandler() {
            @Override
            public void handleMessage(Object message) {
                fishingBoatData.setcBoatCoordinate((Coordinate) message);
            }
        }, true);
    }

    @Override
    public int getReward() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    protected void clearEvent() {
        // TODO Auto-generated method stub

    }

    @Override
    public void start() {
        // TODO Auto-generated method stub

    }

    @Override
    public void update(Observable o, Object arg) {
        Vector3 vector = cAccelmeter.update();
        double newRotation = fishingBoatData.getcCraneRotation() + 0.01d;
        fishingBoatData.setcCraneRotation(newRotation);
        cOtherClient.send(newRotation);

        cDataSubject.update(fishingBoatData);
    }
}

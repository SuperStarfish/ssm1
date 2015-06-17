package cg.group4.game_logic.stroll.events.mp_fishingboat;

import cg.group4.data_structures.mp_fishingboat.Coordinate;
import cg.group4.data_structures.mp_fishingboat.FishingBoatData;
import cg.group4.data_structures.mp_fishingboat.SmallFishData;
import cg.group4.data_structures.mp_fishingboat.SmallFishDestination;
import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.multiplayer_event.Host;
import cg.group4.game_logic.stroll.events.multiplayer_event.MessageHandler;
import cg.group4.game_logic.stroll.events.multiplayer_event.MultiplayerClient;
import cg.group4.util.sensor.Accelerometer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.Observable;

public class FishingBoatClient extends FishingBoatEvent {

    protected MultiplayerClient cOtherClient;

    protected FishingBoatData fishingBoatData;

    ArrayList<Integer> toRemove = new ArrayList<>();

    protected Accelerometer cAccelmeter;

    public FishingBoatClient(Host host) {
        super();
        cOtherClient = (MultiplayerClient) host;
        cAccelmeter = new Accelerometer(StandUp.getInstance().getSensorReader());
        cAccelmeter.filterGravity(false);
        cAccelmeter.setNoiseThreshold(0.5f);
        cAccelmeter.setFilterPerAxis(true);

        fishingBoatData = new FishingBoatData();

        cOtherClient.receiveTCP(new MessageHandler() {
            @Override
            public void handleMessage(Object message) {
                if(message instanceof FishingBoatData) {
                    fishingBoatData = (FishingBoatData) message;
                    cOtherClient.sendTCP(true);
                    keepListening();
                }
            }
        }, false);
    }

    protected void keepListening() {
        cOtherClient.receiveUDP(new MessageHandler() {
            @Override
            public void handleMessage(Object message) {
                fishingBoatData.setcBoatCoordinate((Coordinate) message);
            }
        }, true);

        cOtherClient.receiveTCP(new MessageHandler() {
            @Override
            public void handleMessage(Object message) {
                SmallFishDestination data = (SmallFishDestination) message;
                SmallFishData fish = fishingBoatData.getcSmallFishCoordinates().get(data.getcId());
                if(data.getcNewDestination() == null) {
                    fish.setPosition(null);
                    System.out.println("Received a delete");
                } else {
                    fish.setDestination(data.getcNewDestination());
                }
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

    }

    @Override
    public void update(Observable o, Object arg) {
        Vector3 vector = cAccelmeter.update();
        double newRotation = fishingBoatData.getcCraneRotation() + 0.01d;
        fishingBoatData.setcCraneRotation(newRotation);
        cOtherClient.sendUDP(newRotation);
        moveFish();
        cDataSubject.update(fishingBoatData);
        if(toRemove.size() > 0) {
            for (int key : toRemove) {
                fishingBoatData.getcSmallFishCoordinates().remove(key);
            }
            toRemove.clear();
        }
    }

    protected void moveFish() {
        for (int key : fishingBoatData.getcSmallFishCoordinates().keySet()) {
            SmallFishData fish = fishingBoatData.getcSmallFishCoordinates().get(key);
            if(fish.getPosition() == null) {
                toRemove.add(key);
            } else {
                fish.move();
            }
        }
    }

}

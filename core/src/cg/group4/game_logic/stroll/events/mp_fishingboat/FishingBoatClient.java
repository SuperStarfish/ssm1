package cg.group4.game_logic.stroll.events.mp_fishingboat;

import cg.group4.data_structures.mp_fishingboat.BoatData;
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
        cAccelmeter.setNoiseThreshold(0.2f);
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
                fishingBoatData.setcBoatData((BoatData) message);
            }
        }, true);

        cOtherClient.receiveTCP(new MessageHandler() {
            @Override
            public void handleMessage(Object message) {
                SmallFishDestination data = (SmallFishDestination) message;
                SmallFishData fish = fishingBoatData.getcSmallFishCoordinates().get(data.getcId());
                if(data.getcNewDestination() == null) {
                    System.out.println("received delete!");
                    fish.setPosition(null);
                } else {
                    fish.setDestination(data.getcNewDestination());
                }
            }
        }, true);
    }

    @Override
    public int getReward() {
        // TODO Auto-generated method stub
        return 30;
    }

    @Override
    protected void clearEvent() {
        super.dispose();
        cOtherClient.dispose();

    }

    @Override
    public void start() {

    }

    @Override
    public void update(Observable o, Object arg) {
        Vector3 vector = cAccelmeter.update();

//        double newRotation = Math.atan2(-vector.y, -vector.x);
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
        if(fishingBoatData.getcSmallFishCoordinates().size() == 0) {
            clearEvent();
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

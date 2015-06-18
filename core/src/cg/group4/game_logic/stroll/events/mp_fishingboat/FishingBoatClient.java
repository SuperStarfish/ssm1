package cg.group4.game_logic.stroll.events.mp_fishingboat;

import cg.group4.data_structures.mp_fishingboat.BoatData;
import cg.group4.data_structures.mp_fishingboat.FishingBoatEventData;
import cg.group4.data_structures.mp_fishingboat.SmallFishData;
import cg.group4.data_structures.mp_fishingboat.SmallFishDestination;
import cg.group4.game_logic.stroll.events.multiplayer_event.Host;
import cg.group4.game_logic.stroll.events.multiplayer_event.MessageHandler;
import com.badlogic.gdx.math.Vector3;

import java.util.Observable;

/**
 * Creates a new Client for the FishingBoatEvent.
 */
public class FishingBoatClient extends FishingBoatEvent {

    /**
     * Creates a new CraneFishingEvent Client.
     * @param host the connection with the other host.
     */
    public FishingBoatClient(Host host) {
        super(host);

        cOtherClient.receiveTCP(new MessageHandler() {
            @Override
            public void handleMessage(Object message) {
                if (message instanceof FishingBoatEventData) {
                    cFishingBoatEventData = (FishingBoatEventData) message;
                    cOtherClient.sendTCP(true);
                    keepListening();
                }
            }
        }, false);
    }

    /**
     * Actions to take when receiving incoming UDP or TCP messages.
     */
    protected void keepListening() {
        cOtherClient.receiveUDP(new MessageHandler() {
            @Override
            public void handleMessage(Object message) {
                cFishingBoatEventData.setcBoatData((BoatData) message);
            }
        }, true);

        cOtherClient.receiveTCP(new MessageHandler() {
            @Override
            public void handleMessage(Object message) {
                SmallFishDestination data = (SmallFishDestination) message;
                SmallFishData fish = cFishingBoatEventData.getcSmallFishCoordinates().get(data.getcId());
                if (data.getcNewDestination() == null) {
                    System.out.println("received delete!");
                    fish.setPosition(null);
                } else {
                    fish.setDestination(data.getcNewDestination());
                }
            }
        }, true);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(Observable o, Object arg) {
        Vector3 vector = cAccelerometer.update();

        double newRotation = Math.atan2(-vector.y, -vector.x);
//        double newRotation = cFishingBoatEventData.getcCraneRotation() + 0.01d;
        cFishingBoatEventData.setcCraneRotation(newRotation);
        cOtherClient.sendUDP(newRotation);
        moveFish();
        cDataSubject.update(cFishingBoatEventData);
        validateFish();
    }

    /**
     * Moves the fishes using the data received from the host.
     */
    protected void moveFish() {
        for (int key : cFishingBoatEventData.getcSmallFishCoordinates().keySet()) {
            SmallFishData fish = cFishingBoatEventData.getcSmallFishCoordinates().get(key);
            if (fish.getPosition() == null) {
                cToRemove.add(key);
            } else {
                fish.move();
            }
        }
    }

}

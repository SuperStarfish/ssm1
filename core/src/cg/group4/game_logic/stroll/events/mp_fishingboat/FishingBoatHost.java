package cg.group4.game_logic.stroll.events.mp_fishingboat;

import cg.group4.data_structures.mp_fishingboat.Coordinate;
import cg.group4.data_structures.mp_fishingboat.FishingBoatData;
import cg.group4.data_structures.mp_fishingboat.SmallFishData;
import cg.group4.data_structures.mp_fishingboat.SmallFishDestination;
import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.multiplayer_event.Host;
import cg.group4.game_logic.stroll.events.multiplayer_event.MessageHandler;
import cg.group4.game_logic.stroll.events.multiplayer_event.MultiplayerHost;
import cg.group4.util.sensor.Accelerometer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class FishingBoatHost extends FishingBoatEvent {

    protected MultiplayerHost cOtherClient;

    protected FishingBoatData fishingBoatData;

    ArrayList<Integer> toRemove = new ArrayList<>();

    protected Accelerometer cAccelmeter;
    protected float cSpeed = 0.0025f;
    double hitboxSize = 16 / 1440d;
    double radius = FishingBoatData.getBoatSize() / 2 - hitboxSize / 2;

    public FishingBoatHost(Host host) {
        super();
        cOtherClient = (MultiplayerHost) host;

        cAccelmeter = new Accelerometer(StandUp.getInstance().getSensorReader());
        cAccelmeter.filterGravity(false);
        cAccelmeter.setNoiseThreshold(0.5f);
        cAccelmeter.setFilterPerAxis(true);

        fishingBoatData = new FishingBoatData();

        cOtherClient.sendTCP(fishingBoatData);

        cOtherClient.receiveTCP(new MessageHandler() {
            @Override
            public void handleMessage(Object message) {
                cOtherClient.receiveUDP(new MessageHandler() {
                    @Override
                    public void handleMessage(Object message) {
                        fishingBoatData.setcCraneRotation((Double) message);
                    }
                }, true);
            }
        }, false);

//        cOtherClient.receiveUDP(new MessageHandler() {
//            @Override
//            public void handleMessage(Object message) {
//                fishingBoatData.setcCraneRotation((Double) message);
//            }
//        }, true);


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
        moveBoat(vector);
        moveFish();
//        cOtherClient.sendTCP(fishingBoatData.getcBoatCoordinate());
        cOtherClient.sendUDP(fishingBoatData.getcBoatCoordinate());
        cDataSubject.update(fishingBoatData);
        if(toRemove.size() > 0) {
            for (int key : toRemove) {
                fishingBoatData.getcSmallFishCoordinates().remove(key);
            }
            toRemove.clear();
        }
    }

    protected void moveFish() {
        HashMap<Integer, SmallFishData> data = fishingBoatData.getcSmallFishCoordinates();

        Coordinate boatLocation = fishingBoatData.getcBoatCoordinate();

        double xCoordCenter = boatLocation.getX() + radius;
        double yCoordCenter = boatLocation.getY() + radius;

        double angle = fishingBoatData.getcCraneRotation();

        double xPositionMin = Math.cos(angle) * radius + xCoordCenter;
        double xPositionMax = xPositionMin + hitboxSize;
        double yPositionMin = Math.sin(angle) * radius + yCoordCenter;
        double yPositionMax = yPositionMin + hitboxSize;

        for(int key : fishingBoatData.getcSmallFishCoordinates().keySet()) {
            SmallFishData fish = data.get(key);
            fish.move();

            if(fish.intersects(xPositionMin, xPositionMax, yPositionMin, yPositionMax)) {
                fish.setPosition(null);
                cOtherClient.sendTCP(new SmallFishDestination(key, null));
                toRemove.add(key);
            } else if(fish.destinationReached()) {
                Coordinate newDestination = fish.generatePosition();
                fish.setDestination(newDestination);
                cOtherClient.sendTCP(new SmallFishDestination(key, newDestination));
            }
        }
    }

    protected void moveBoat(Vector3 vector) {
        float totalForce = Math.abs(vector.x) + Math.abs(vector.y);
        Coordinate boatCoordinate = fishingBoatData.getcBoatCoordinate();
        float oldX = boatCoordinate.getX();
        float oldY = boatCoordinate.getY();
        if(totalForce != 0) {
            float newX = oldX - cSpeed * (vector.x / totalForce);
            float newY = oldY - cSpeed * (vector.y / totalForce);
            if(newX >= 0 && newX <= 1 - FishingBoatData.getBoatSize()) {
                boatCoordinate.setX(newX);
            }
            if(newY >= 0 && newY <= 1 - FishingBoatData.getBoatSize()) {
                boatCoordinate.setY(newY);
            }
        }
    }
}
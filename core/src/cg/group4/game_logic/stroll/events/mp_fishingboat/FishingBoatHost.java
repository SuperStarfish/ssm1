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
    double hitboxXRadius = 8 / 1440d;
    double hitboxYRadius = 8 / 2560d;
    double xRadius = 128d / 1440d;
    double yRadius = 128d / 2560d;

    public FishingBoatHost(Host host) {
        super();
        cOtherClient = (MultiplayerHost) host;

        cAccelmeter = new Accelerometer(StandUp.getInstance().getSensorReader());
        cAccelmeter.filterGravity(false);
        cAccelmeter.setNoiseThreshold(0.2f);
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
        moveBoat(vector);
        moveFish();
        cOtherClient.sendUDP(fishingBoatData.getcBoatData());
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
        HashMap<Integer, SmallFishData> data = fishingBoatData.getcSmallFishCoordinates();

        Coordinate boatLocation = fishingBoatData.getcBoatData().getcLocation();

        double xCoordCenter = boatLocation.getX() + xRadius - hitboxXRadius;
        double yCoordCenter = boatLocation.getY() + yRadius - hitboxYRadius;

        double angle = fishingBoatData.getcCraneRotation();

        double xPositionMin = xCoordCenter + Math.cos(angle) * (xRadius - hitboxXRadius);
        double yPositionMin = yCoordCenter + Math.sin(angle) * (yRadius - hitboxYRadius);
        double xPositionMax = xPositionMin + hitboxXRadius * 2;
        double yPositionMax = yPositionMin + hitboxYRadius * 2;

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

        Coordinate boatCoordinate = fishingBoatData.getcBoatData().getcLocation();

        double oldX = boatCoordinate.getX();
        double oldY = boatCoordinate.getY();
        if(totalForce != 0) {
            double angle = Math.atan2(-vector.y, -vector.x);
            fishingBoatData.getcBoatData().setcRotation(angle);

            float newX = (float)oldX - cSpeed * (vector.x / totalForce);
            float newY = (float)oldY - cSpeed * (vector.y / totalForce);
            if(newX >= 0 && newX <= 1 - (xRadius * 2)) {
                boatCoordinate.setX(newX);
            }
            if(newY >= 0 && newY <= 1 - (yRadius * 2)) {
                boatCoordinate.setY(newY);
            }
        }
    }
}
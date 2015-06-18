package cg.group4.game_logic.stroll.events.mp_fishingboat;

import cg.group4.data_structures.mp_fishingboat.Coordinate;
import cg.group4.data_structures.mp_fishingboat.SmallFishData;
import cg.group4.data_structures.mp_fishingboat.SmallFishDestination;
import cg.group4.game_logic.stroll.events.multiplayer_event.Host;
import cg.group4.game_logic.stroll.events.multiplayer_event.MessageHandler;
import com.badlogic.gdx.math.Vector3;

import java.util.HashMap;
import java.util.Observable;

/**
 * Creates a new Host for the FishingBoatEvent.
 */
public class FishingBoatHost extends FishingBoatEvent {
    /**
     * Coordinate used to debug hitbox.
     */
    protected Coordinate cHitboxLocation = new Coordinate(0, 0);
    /**
     * Speed of the boat.
     */
    protected final float cSpeed = 0.003f;
    /**
     * Half the size of the HitBox.
     */
    protected final int cHitBoxRadius = 8;
    /**
     * Half the size of the Boat.
     */
    protected final int cBoatRadius = 128;
    /**
     * The Development width (9:16) used.
     */
    protected final double cDevWidth = 1440d;
    /**
     * The Development width (9:16) used.
     */
    protected final double cDevHeight = 2560d;
    /**
     * HitBox radius for 9:16 on the X axis.
     */
    protected final double cHitboxXRadius = cHitBoxRadius / cDevWidth;
    /**
     * HitBox radius for 9:16 on the Y axis.
     */
    protected final double cHitboxYRadius = cHitBoxRadius / cDevHeight;
    /**
     * Boat radius for 9:16 on the X axis.
     */
    protected final double cXRadius = cBoatRadius / cDevWidth;
    /**
     * Boat radius for 9:16 on the Y axis.
     */
    protected final double cYRadius = cBoatRadius / cDevHeight;

    /**
     * Creates a new CraneFishingEvent Host.
     * @param host the connection with the other client.
     */
    public FishingBoatHost(Host host) {
        super(host);
        cOtherClient.sendTCP(cFishingBoatEventData);
    }

    @Override
    public void start() {
        cOtherClient.receiveUDP(new MessageHandler() {
            @Override
            public void handleMessage(Object message) {
                cFishingBoatEventData.setcCraneRotation((double) message);
            }
        }, true);
    }

    @Override
    public void update(Observable o, Object arg) {
        Vector3 vector = cAccelerometer.update();
        moveBoat(vector);
        moveFish();
        cOtherClient.sendUDP(cFishingBoatEventData.getcBoatData());
        cDataSubject.update(cFishingBoatEventData);
//        cDataSubject.update(cHitboxLocation);
        validateFish();
    }

    /**
     * Moves the fish and determines if the fish is caught or if the destination is reached.
     * New destination and 'removal' of the fish are also send to the other client.
     */
    protected void moveFish() {
        HashMap<Integer, SmallFishData> data = cFishingBoatEventData.getcSmallFishCoordinates();

        Coordinate boatLocation = cFishingBoatEventData.getcBoatData().getcLocation();

        double xCoordCenter = boatLocation.getX() + cXRadius - cHitboxXRadius;
        double yCoordCenter = boatLocation.getY() + cYRadius - cHitboxYRadius;

        double angle = cFishingBoatEventData.getcCraneRotation();

        double xPositionMin = xCoordCenter + Math.cos(angle) * (cXRadius - cHitboxXRadius);
        double yPositionMin = yCoordCenter + Math.sin(angle) * (cYRadius - cHitboxYRadius);
        double xPositionMax = xPositionMin + cHitboxXRadius * 2;
        double yPositionMax = yPositionMin + cHitboxYRadius * 2;

        //
        cHitboxLocation.setX((float) xPositionMin);
        cHitboxLocation.setY((float) yPositionMin);

        for (int key : cFishingBoatEventData.getcSmallFishCoordinates().keySet()) {
            SmallFishData fish = data.get(key);

            fish.move();

            if (fish.intersects(xPositionMin, xPositionMax, yPositionMin, yPositionMax)) {
                fish.setPosition(null);
                cOtherClient.sendTCP(new SmallFishDestination(key, null));
                cToRemove.add(key);
            } else if (fish.destinationReached()) {
                Coordinate newDestination = fish.generatePosition();
                fish.setDestination(newDestination);
                cOtherClient.sendTCP(new SmallFishDestination(key, newDestination));
            }
        }
    }

    /**
     * Moves the boat given the accelerometer values. Also updates the other client.
     * @param vector The accelerometer values.
     */
    protected void moveBoat(Vector3 vector) {
        float totalForce = Math.abs(vector.x) + Math.abs(vector.y);

        Coordinate boatCoordinate = cFishingBoatEventData.getcBoatData().getcLocation();

        double oldX = boatCoordinate.getX();
        double oldY = boatCoordinate.getY();
        if (totalForce != 0) {
            double angle = Math.atan2(-vector.y, -vector.x);
            cFishingBoatEventData.getcBoatData().setcRotation(angle);

            float newX = (float) oldX - cSpeed * (vector.x / totalForce);
            float newY = (float) oldY - cSpeed * (vector.y / totalForce);
            if (newX >= 0 && newX <= 1 - (cXRadius * 2)) {
                boatCoordinate.setX(newX);
            }
            if (newY >= 0 && newY <= 1 - (cYRadius * 2)) {
                boatCoordinate.setY(newY);
            }
        }
    }
}
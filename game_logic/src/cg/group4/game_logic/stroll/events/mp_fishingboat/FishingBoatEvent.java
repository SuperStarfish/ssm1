package cg.group4.game_logic.stroll.events.mp_fishingboat;

import cg.group4.data_structures.mp_fishingboat.FishingBoatEventData;
import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.game_logic.stroll.events.multiplayer_event.Host;
import cg.group4.util.sensor.Accelerometer;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * Defaults for both the client and the host part of the CraneFishing MultiPlayer Event.
 */
public abstract class FishingBoatEvent extends StrollEvent {
    /**
     * Lowers the noise threshold to make it less 'snappy' to the X and Y axis.
     */
    protected final float cNoiseThreshold = 0.5f;
    /**
     * The reward received when completing the event.
     */
    protected final int cReward = 3;
    /**
     * Connection with the other client.
     */
    protected Host cOtherClient;
    /**
     * Contains all the data for the event.
     */
    protected FishingBoatEventData cFishingBoatEventData;
    /**
     * The accelerometer to determine rotation and direction.
     */
    protected Accelerometer cAccelerometer;
    /**
     * ArrayList to avoid concurrent modification exception when deleting fish (when caught).
     */
    protected ArrayList<Integer> cToRemove = new ArrayList<Integer>();

    /**
     * Observer that will be notified when one of the sides disconnects.
     */
    protected Observer cDisconnectObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            disconnectFromEvent();
        }
    };

    /**
     * Construct a new CraneFishingEvent.
     *
     * @param otherClient Connection with the other client.
     */
    public FishingBoatEvent(Host otherClient) {
        super();
        cOtherClient = otherClient;
        cOtherClient.getcDisconnectSubject().addObserver(cDisconnectObserver);
        cAccelerometer = new Accelerometer(StandUp.getInstance().getSensorReader());
        cAccelerometer.filterGravity(false);
        cAccelerometer.setNoiseThreshold(cNoiseThreshold);
        cAccelerometer.setFilterPerAxis(true);

        cFishingBoatEventData = new FishingBoatEventData();
    }

    /**
     * Checks to see if there are any fish to delete and if the event is finished.
     */
    protected void validateFish() {
        if (cToRemove.size() > 0) {
            for (int key : cToRemove) {
                cFishingBoatEventData.getcSmallFishCoordinates().remove(key);
            }
            cToRemove.clear();
        }
        if (cFishingBoatEventData.getcSmallFishCoordinates().size() == 0) {
            clearEvent();
        }
    }

    @Override
    protected void clearEvent() {
        super.dispose();
        cOtherClient.dispose();
    }

    @Override
    public void dispose(boolean eventCompleted) {
        cOtherClient.dispose();
        super.dispose(eventCompleted);
    }

    @Override
    public int getReward() {
        return cReward;
    }

    /**
     * Called when disconnected from the other player. Clears the event without giving rewards.
     */
    protected void disconnectFromEvent() {
        System.out.println("I SHOULD DIE HERE");
        super.dispose(false);
        cOtherClient.dispose();
    }

}

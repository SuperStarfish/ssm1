package cg.group4.game_logic.stroll;

import cg.group4.client.Client;
import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.collection.RewardGenerator;
import cg.group4.data_structures.subscribe.Subject;
import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.events.FollowTheFishEvent;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.game_logic.stroll.events.fishevent.FishingStrollEvent;
import cg.group4.game_logic.stroll.events.mp_fishingboat.FishingBoatClient;
import cg.group4.game_logic.stroll.events.mp_fishingboat.FishingBoatHost;
import cg.group4.game_logic.stroll.events.multiplayer_event.Host;
import cg.group4.game_logic.stroll.events.multiplayer_event.MessageHandler;
import cg.group4.game_logic.stroll.events.multiplayer_event.MultiplayerClient;
import cg.group4.game_logic.stroll.events.multiplayer_event.MultiplayerHost;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;
import cg.group4.util.sensor.AccelerationState;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * A stroll will generate events for the player based on the players activity.
 */
public class Stroll implements Observer {

    /**
     * The base threshold used for generating events.
     */
    protected static final double BASE_THRESHOLD = 0.002;

    /**
     * Tag used for debugging.
     */
    private static final String TAG = Stroll.class.getSimpleName();

    /**
     * The chance an event occurs.
     */
    protected double cEventThreshold;
    /**
     * Score of each completed event.
     */
    protected ArrayList<Integer> cRewards;
    /**
     * Whether or not you're busy with an event.
     */
    protected boolean cEventGoing;
    /**
     * Whether the stroll has ended or not.
     */
    protected Boolean cFinished;
    /**
     * The current event being played.
     */
    protected StrollEvent cEvent;
    /**
     * Subject for end of stroll.
     */
    protected Subject cEndStrollSubject;
    /**
     * The stroll timer.
     */
    protected Timer cStrollTimer;
    /**
     * The number of MultiPlayer events.
     */
    protected final int cNumberOfSinglePlayerEvents = 2;
    /**
     * The number of MultiPlayer events.
     */
    protected final int cNumberOfMultiPlayerEvents = 1;

    /**
     * The observer to subscribe to the stop subject of stroll timer.
     */
    protected Observer cStrollStopObserver = new Observer() {

        @Override
        public void update(final Observable o, final Object arg) {
            cFinished = true;
            if (!cEventGoing) {
                done();
            }
        }
    };

    /**
     * Listens to the movements made by the player during the stroll. Changes the event spawn threshold according
     * to what is defined for that state.
     */
    protected Observer cUpdateMovementObserver = new Observer() {

        @Override
        public void update(final Observable o, final Object arg) {
            cEventThreshold = BASE_THRESHOLD * getAmplifier((AccelerationState) arg);
        }
    };

    /**
     * Subject for new event.
     */
    protected Subject cNewEventSubject;

    /**
     * Subject for end of event.
     */
    protected Subject cEndEventSubject;

    /**
     * Constructor, creates a new Stroll object.
     */
    public Stroll() {
        Gdx.app.log(TAG, "Started new stroll");
        cRewards = new ArrayList<Integer>();
        cEventGoing = false;
        cEventThreshold = getAmplifier(StandUp.getInstance().
                getAccelerationStatus().getAccelerationState());
        cFinished = false;
        cEndStrollSubject = new Subject();
        cNewEventSubject = new Subject();
        cEndEventSubject = new Subject();

        StandUp.getInstance().getUpdateSubject().addObserver(this);
        StandUp.getInstance().getAccelerationStatus().
                getSubject().addObserver(cUpdateMovementObserver);

        cStrollTimer = TimerStore.getInstance().getTimer(Timer.Global.STROLL.name());
        cStrollTimer.getStopSubject().addObserver(cStrollStopObserver);

        cStrollTimer.reset();
    }

    /**
     * Method that returns the amplifier for the event chance.
     *
     * @param state Movement state gotten from the AccelLib library.
     * @return Integer used to amplify the chance of getting the event.
     */
    protected final int getAmplifier(final AccelerationState state) {
        for (Amplifier a : Amplifier.values()) {
            if (a.cState == state) {
                return a.cAmplifier;
            }
        }
        return 0;
    }

    @Override
    public final void update(final Observable o, final Object arg) {
        if (!cEventGoing) {
            generatePossibleEvent();
        }
    }

    /**
     * Starts the hosting of a multi player event.
     *
     * @param responseHandler A response handler that will be called with the connection code.
     */
    public void startMultiPlayerEvent(final ResponseHandler responseHandler) {
        if (Client.getRemoteInstance().isConnected()) {
            Gdx.app.log(TAG, "Start hosting multi-player event!");
            cEventGoing = true;
            Client.getRemoteInstance().hostEvent(whenHostCodeReceived(responseHandler));
        }
    }

    /**
     * Defines behaviour that is executed when a Code is received from the server.
     * @param updateUI ResponseHandler that updates the UI.
     * @return A ResponseHandler with extra behaviour.
     */
    protected ResponseHandler whenHostCodeReceived(final ResponseHandler updateUI) {
        return new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                updateUI.handleResponse(response);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        createMultiPlayerHost();
                    }
                }).start();
            }
        };
    }

    /**
     * Creates a MultiPlayer host.
     */
    protected void createMultiPlayerHost() {
        final MultiplayerHost host = new MultiplayerHost();
        host.connect();
        Random rng = new Random();
        final int event = rng.nextInt(cNumberOfMultiPlayerEvents);
        host.sendTCP(event);
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                generatePossibleMultiplayerEvent(event, host);
            }
        });
    }

    /**
     * Joins a MultiPlayer game.
     * @param code The code to find the host.
     * @param responseHandler Behaviour to execute when reply is received.
     */
    public void joinMultiPlayerEvent(final Integer code, final ResponseHandler responseHandler) {
        if (Client.getRemoteInstance().isConnected()) {
            Gdx.app.log(TAG, "Joining multi-player event!");
            cEventGoing = true;
            Client.getRemoteInstance().getHost(code, whenHostIPReceived(responseHandler));
        }
    }

    /**
     * Adds extra behaviour when Host IP is received.
     * @param updateUI Updates the UI accordingly.
     * @return Extra behaviour in the provided ResponseHandler.
     */
    protected ResponseHandler whenHostIPReceived(final ResponseHandler updateUI) {
        return new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                if (response.isSuccess()) {
                    createMultiPlayerClient((String) response.getData());
                } else {
                    updateUI.handleResponse(response);
                }
            }
        };
    }

    /**
     * Creates a MultiPlayer Client.
     * @param ip The IP to connect to.
     */
    protected void createMultiPlayerClient(final String ip) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final MultiplayerClient client = new MultiplayerClient(ip);
                    client.connect();

                    client.receiveTCP(new MessageHandler() {
                        @Override
                        public void handleMessage(Object message) {
                            generatePossibleMultiplayerEvent((Integer) message, client);
                        }
                    }, false);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }



    /**
     * Handles completion of an event.
     *
     * @param rewards Reward(s) given on completion of an event
     */
    public final void eventFinished(final int rewards) {
        Gdx.app.log(TAG, "Event completed! Collected " + rewards + " rewards.");

        cRewards.add(rewards);

        cancelEvent();
    }

    /**
     * Generate an event on a certain requirement (e.g. a random r: float < 0.1).
     */
    protected void generatePossibleEvent() {
        Random rng = new Random();
        if (rng.nextDouble() < cEventThreshold) {
            cEventGoing = true;
            switch (rng.nextInt(cNumberOfSinglePlayerEvents)) {
                case (0):
                    cEvent = new FishingStrollEvent();
                    break;
                default:
                    cEvent = new FollowTheFishEvent();
                    break;
            }
            cNewEventSubject.update(cEvent);
        }
    }

    protected void generatePossibleMultiplayerEvent(int event, Host host) {
        cEventGoing = true;
        switch (event) {
            default:
                if (host.isHost()) {
                    cEvent = new FishingBoatHost(host);
                } else {
                    cEvent = new FishingBoatClient(host);
                }
                break;
        }
        cNewEventSubject.update(cEvent);
    }

    /**
     * handles cancellation of an event.
     */
    public void cancelEvent() {
        Gdx.app.log(TAG, "Event stopped!");

        cEndEventSubject.update();

        cEvent = null;
        cEventGoing = false;

        if (cFinished) {
            Gdx.app.log(TAG, "Event finished and time is up, ending stroll.");
            done();
        }
    }

    /**
     * Method that gets called when the stroll has ended/completed.
     */
    public void done() {
        Gdx.app.log(TAG, "Stroll has ended. Collected " + cRewards + " rewards.");

        RewardGenerator gen = new RewardGenerator(StandUp.getInstance().getPlayer().getId());
        Collection collection = new Collection("Reward");
        for (Integer i : cRewards) {
            collection.add(gen.generateCollectible(i));
        }

        StandUp.getInstance().getUpdateSubject().deleteObserver(this);

        cEndStrollSubject.update(collection);
        cEndStrollSubject.deleteObservers();

        cStrollTimer.getStopSubject().deleteObserver(cStrollStopObserver);

        StandUp.getInstance().endStroll(collection);
    }

    /**
     * Getter for the subject to subscribe to to get updated for the end of the stroll.
     *
     * @return Subject to subscribe to.
     */
    public final Subject getEndStrollSubject() {
        return cEndStrollSubject;
    }

    /**
     * Getter for the subject to subscribe to get updated for the start of a new event.
     *
     * @return Subject to subscribe to.
     */
    public final Subject getNewEventSubject() {
        return cNewEventSubject;
    }

    /**
     * Getter for the subject to subscribe to to get updated for the end of the event.
     * @return Subject to subscribe to.
     */
    public final Subject getEndEventSubject() {
        return cEndEventSubject;
    }

    /**
     * Amplifier enum.
     */
    public enum Amplifier {

    	/**
    	 * When walking, you should get normal chance of an event.
    	 */
        WALK(AccelerationState.WALKING, 1),

        /**
    	 * When running, you should get double chance of an event.
    	 */
        RUN(AccelerationState.RUNNING, 2),

        /**
    	 * When not moving at all, you should get no event.
    	 */
        STOP(AccelerationState.RESTING, 0),

        /**
    	 * When cheating movements, you should get no events as well.
    	 */
        CHEAT(AccelerationState.CHEATING, 0);

        /**
         * The state of movement.
         */
        private AccelerationState cState;

        /**
         * Amplifier for the movement.
         */
        private int cAmplifier;

        /**
         * Constructor for the Enum.
         * @param state The state of movement.
         * @param amplifier Amplifier for the movement.
         */
        Amplifier(final AccelerationState state, final int amplifier) {
            this.cAmplifier = amplifier;
            this.cState = state;
        }
    }


}

package cg.group4.view.screen;

import cg.group4.client.Client;
import cg.group4.core.game_logic.StandUp;
import cg.group4.core.game_logic.stroll.Stroll;
import cg.group4.core.game_logic.stroll.events.StrollEvent;
import cg.group4.core.game_logic.stroll.events.mp_fishingboat.FishingBoatClient;
import cg.group4.core.game_logic.stroll.events.mp_fishingboat.FishingBoatHost;
import cg.group4.data_structures.collection.Collection;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
import cg.group4.view.screen.mp_fishingboat.CraneFishingScreen;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;

import java.util.Observable;
import java.util.Observer;

/**
 * Screen to be displayed during an event.
 */
public final class StrollScreen extends ScreenLogic {
    /**
     * Labels to display text of the screen.
     */
    protected Label cTimeRemaining, cText;

    /**
     * Table that all the elements are added to.
     */
    protected Table cTable;
    /**
     * TextField where a player can enter the code to join a friend.
     */
    protected TextField cCode;
    /**
     * Buttons to interact with in order to host or join multiplayer games.
     */
    protected TextButton cHost, cJoin;

    /**
     * Observer that gets called when the stroll ends.
     */
    protected Observer cNewEventObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            StrollEvent strollEvent = (StrollEvent) arg;
            EventScreen eventScreen;
            if (arg instanceof FishingBoatHost || arg instanceof FishingBoatClient) {
                eventScreen = new CraneFishingScreen(strollEvent);
            } else {
                eventScreen = new TextEventScreen(strollEvent);
            }
            cScreenStore.addScreen("Event", eventScreen);
            cScreenStore.setScreen("Event");
        }
    };
    /**
     * Observer that gets called when the stroll ends.
     */
    protected Observer cEndStrollObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            cScreenStore.addScreen("Reward", new RewardScreen((Collection) arg));
            cScreenStore.setScreen("Reward");
        }
    };
    /**
     * Listener to when the connection state with the remote server changes.
     */
    protected Observer cRemoteConnectObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            boolean isConnected = (boolean) arg;
            cHost.setDisabled(!isConnected);
            cJoin.setDisabled(!isConnected);
        }
    };
    /**
     * Connection to the remote server.
     */
    protected Client cClient;
    /**
     * Observer that gets called when the stroll ends.
     */
    protected Observer cEndEventObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            cScreenStore.setScreen("Stroll");
            cScreenStore.removeScreen("Event");
            if (!cClient.isRemoteConnected()) {
                cHost.setDisabled(true);
                cJoin.setDisabled(true);
            }
        }
    };
    /**
     * The stroll timer of the game.
     */
    protected Timer cStrollTimer;

    /**
     * Observer to subscribe to the tick subject of the stroll timer.
     */
    protected Observer cStrollTickObserver;

    /**
     * Creates a screen that should be displayed during a stroll.
     */
    public StrollScreen() {
        cScreenStore = ScreenStore.getInstance();
        cClient = Client.getInstance();
        cText = cGameSkin.generateDefaultLabel("Waiting for event");
        cCode = cGameSkin.generateDefaultTextField("Enter code");
        cHost = cGameSkin.generateDefaultMenuButton("Host");
        cJoin = cGameSkin.generateDefaultMenuButton("Join");
        if (!cClient.isRemoteConnected()) {
            cClient.getRemoteChangeSubject().addObserver(cRemoteConnectObserver);
            cHost.setDisabled(true);
            cJoin.setDisabled(true);
        }
        Stroll stroll = StandUp.getInstance().getStroll();
        stroll.getEndStrollSubject().addObserver(cEndStrollObserver);
        stroll.getNewEventSubject().addObserver(cNewEventObserver);
        stroll.getEndEventSubject().addObserver(cEndEventObserver);
    }

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }

    @Override
    protected void rebuildWidgetGroup() {
        cTimeRemaining.setStyle(cGameSkin.getDefaultLabelStyle());
        cText.setStyle(cGameSkin.getDefaultLabelStyle());
        cJoin.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cHost.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        initRemainingTime();
        cCode.setAlignment(Align.center);

        cHost.setDisabled(!cClient.isRemoteConnected());
        cJoin.setDisabled(!cClient.isRemoteConnected());

        cHost.addListener(hostButtonClicked());
        cJoin.addListener(joinButtonClicked());
        return fillTable();
    }

    /**
     * Initializes the label to display the time remaining of the stroll.
     */
    protected void initRemainingTime() {
        cTimeRemaining = cGameSkin.generateDefaultLabel(Integer.toString(Timer.Global.STROLL.getDuration()));
        cStrollTickObserver = new Observer() {
            @Override
            public void update(final Observable o, final Object arg) {
                cTimeRemaining.setText(arg.toString());
            }
        };

        cStrollTimer = TimerStore.getInstance().getTimer(Timer.Global.STROLL.name());
        cStrollTimer.getTickSubject().addObserver(cStrollTickObserver);
    }

    /**
     * Fires when the host button is clicked.
     *
     * @return The behaviour to execute when clicked.
     */
    protected ChangeListener hostButtonClicked() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StandUp.getInstance().getStroll().startMultiPlayerEvent(new ResponseHandler() {
                    @Override
                    public void handleResponse(Response response) {
                        cCode.setText(Integer.toString((Integer) response.getData()));
                        cText.setText("Waiting for other player...");
                        cHost.setDisabled(true);
                        cJoin.setDisabled(true);
                    }
                });
            }
        };
    }

    /**
     * Adds behaviour when the join button is clicked.
     *
     * @return The change listener.
     */
    protected ChangeListener joinButtonClicked() {
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                try {
                    int code = Integer.parseInt(cCode.getText());
                    StandUp.getInstance().getStroll().joinMultiPlayerEvent(code, new ResponseHandler() {
                        @Override
                        public void handleResponse(Response response) {
                            if (!response.isSuccess() || response.getData() == null) {
                                cCode.setText("Wrong code!");
                            }
                        }
                    });
                } catch (NumberFormatException e) {
                    cCode.setText("Not a valid code.");
                }
            }
        };
    }

    /**
     * Fills the table of the screen.
     *
     * @return Returns the filled table.
     */
    public WidgetGroup fillTable() {
        cTable = new Table();
        cTable.setFillParent(true);
        cTable.row().expandY();
        cTable.add(cTimeRemaining).colspan(2);
        cTable.row().expandY();
        cTable.add(cText).colspan(2);
        cTable.row().expandY();
        cTable.add(cCode).fillX().colspan(2);
        cTable.row().expandY();
        cTable.add(cHost);
        cTable.add(cJoin);
        return cTable;
    }

}

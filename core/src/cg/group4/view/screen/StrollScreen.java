package cg.group4.view.screen;

import cg.group4.data_structures.collection.Collection;
import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.Stroll;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerStore;
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

    protected TextField cCode;

    protected TextButton cHost, cJoin;

    /**
     * Observer that gets called when the stroll ends.
     */
    protected Observer cNewEventObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            cScreenStore.addScreen("Event", new EventScreen((StrollEvent) arg));
            cScreenStore.setScreen("Event");
        }
    };

    /**
     * Observer that gets called when the stroll ends.
     */
    protected Observer cEndEventObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            cScreenStore.setScreen("Stroll");
            cScreenStore.removeScreen("Event");
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
        cText = cGameSkin.generateDefaultLabel("Waiting for event");
        cCode = cGameSkin.generateDefaultTextField("Enter code");
        cHost = cGameSkin.generateDefaultMenuButton("Host");
        cJoin = cGameSkin.generateDefaultMenuButton("Join");
        Stroll stroll = StandUp.getInstance().getStroll();
        stroll.getEndStrollSubject().addObserver(cEndStrollObserver);
        stroll.getNewEventSubject().addObserver(cNewEventObserver);
        stroll.getEndEventSubject().addObserver(cEndEventObserver);
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        initRemainingTime();
        cCode.setAlignment(Align.center);
        cHost.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StandUp.getInstance().getStroll().startMultiPlayerEvent(new ResponseHandler() {
                    @Override
                    public void handleResponse(Response response) {
                        cCode.setText(Integer.toString((Integer) response.getData()));
                    }
                });
            }
        });
        cJoin.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StandUp.getInstance().getStroll().joinMultiPlayerEvent(
                        Integer.parseInt(cCode.getText()), new ResponseHandler() {
                            @Override
                            public void handleResponse(Response response) {
                                String ip = (String) response.getData();
                                if (ip == null) {
                                    ip = "Wrong code!";
                                }
                                cCode.setText(ip);
                            }
                        });
            }
        });
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

    @Override
    protected void rebuildWidgetGroup() {
        cTimeRemaining.setStyle(cGameSkin.getDefaultLabelStyle());
        cText.setStyle(cGameSkin.getDefaultLabelStyle());
        cJoin.setStyle(cGameSkin.getDefaultTextButtonStyle());
        cHost.setStyle(cGameSkin.getDefaultTextButtonStyle());
    }

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }
}

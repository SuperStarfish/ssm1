package cg.group4.view.screen;

import cg.group4.game_logic.StandUp;
import cg.group4.game_logic.stroll.Stroll;
import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.Timer;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

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
     * Screen store of the game.
     */
    protected ScreenStore cScreenStore;

    /**
     * Observer that gets called when the stroll ends.
     */
    protected Observer cNewEventObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            cScreenStore.addScreen("Event", ((StrollEvent) arg).createScreen());
            cScreenStore.setScreen("Event");
        }
    };

    /**
     * Observer that gets called when the stroll ends.
     */
    protected Observer cEndEventObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            cScreenStore.setScreen("Stroll");
            cScreenStore.removeScreen("Event");
        }
    };

    /**
     * Observer that gets called when the stroll ends.
     */
    protected Observer cEndStrollObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            cScreenStore.addScreen("Reward", new RewardScreen((Integer) arg));
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
        Stroll stroll = StandUp.getInstance().getStroll();
        stroll.getEndStrollSubject().addObserver(cEndStrollObserver);
        stroll.getNewEventSubject().addObserver(cNewEventObserver);
        stroll.getEndEventSubject().addObserver(cEndEventObserver);
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        cTable = new Table();
        cTable.setFillParent(true);
        cTable.row().expandY();

        initRemainingTime();

        cTable.row().expandY();
        cText = new Label("Waiting for event", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        cTable.add(cText);

        return cTable;
    }

    /**
     * Initializes the label to display the time remaining of the stroll.
     */
    protected void initRemainingTime() {
        cTimeRemaining = new Label(
                Integer.toString(Timer.Global.STROLL.getDuration()),
                cGameSkin.get("default_labelStyle", Label.LabelStyle.class));

        cStrollTickObserver = new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                cTimeRemaining.setText(arg.toString());
            }
        };

        cStrollTimer = TimeKeeper.getInstance().getTimer(Timer.Global.STROLL.name());
        cStrollTimer.getTickSubject().addObserver(cStrollTickObserver);

        cTable.add(cTimeRemaining);
    }

    @Override
    protected void rebuildWidgetGroup() {
        cTimeRemaining.setStyle(cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        cText.setStyle(cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
    }

    @Override
    protected String setPreviousScreenName() {
        return "Home";
    }
}

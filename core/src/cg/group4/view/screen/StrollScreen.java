package cg.group4.view.screen;

import cg.group4.game_logic.StandUp;
import cg.group4.stroll.Stroll;
import cg.group4.stroll.events.StrollEvent;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import java.util.Observable;
import java.util.Observer;

public class StrollScreen extends ScreenLogic {
    protected Label cTimeRemaining, cText;
    protected Table cTable;
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
            cScreenStore.addScreen("Reward", new RewardScreen());
            cScreenStore.setScreen("Reward");
        }
    };

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
        cTimeRemaining = new Label("300", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        TimeKeeper.getInstance().getTimer(Timer.Global.STROLL.name())
                .subscribe(new TimerTask() {
                    @Override
                    public void onTick(int seconds) {
                        cTimeRemaining.setText(Integer.toString(seconds));
                    }

                    @Override
                    public void onStart(int seconds) {

                    }

                    @Override
                    public void onStop() {

                    }
                });
        cTable.add(cTimeRemaining);

        cTable.row().expandY();
        cText = new Label("Waiting for event", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        cTable.add(cText);

        return cTable;
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

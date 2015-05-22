package cg.group4.view.screen;

import cg.group4.game_logic.StandUp;
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

    /**
     * Observer that gets called when the stroll ends.
     */
    protected Observer cEndStrollObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            ScreenStore screenStore = ScreenStore.getInstance();
            screenStore.addScreen("Reward", new RewardScreen());
            screenStore.setScreen("Reward");
        }
    };

    public StrollScreen() {
        StandUp.getInstance().getStroll().getEndStrollSubject().addObserver(cEndStrollObserver);
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
}

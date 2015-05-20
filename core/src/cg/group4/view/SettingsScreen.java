package cg.group4.view;

import cg.group4.game_logic.StandUp;
import cg.group4.util.camera.WorldRenderer;
import cg.group4.util.timer.Timer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class SettingsScreen extends ScreenLogic{
    Table cTable;

    TextButton cButtonResetInterval,
            cButtonResetStroll,
            cButtonStopInterval,
            cButtonBack;

    public SettingsScreen(WorldRenderer worldRenderer) {
        super(worldRenderer);
        createTable();
        createGUI();

        cWorldRenderer.setActor(cTable);
    }

    protected void createTable(){
        cTable = new Table();
        cTable.setFillParent(true);
        cTable.debugAll();
    }

    protected void createGUI(){
        cButtonResetInterval = createButon("Reset Interval");
        cButtonResetInterval.addListener(resetIntervalBehaviour());

        cButtonResetStroll = createButon("Reset Stroll");
        cButtonResetStroll.addListener(resetStrollBehaviour());

        cButtonStopInterval = createButon("Stop Interval");
        cButtonStopInterval.addListener(stopIntervalBehaviour());

        cButtonBack = createButon("Back");
        cButtonBack.addListener(backBehaviour());

    }

    protected ChangeListener resetIntervalBehaviour(){
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StandUp.getInstance().getTimeKeeper().getTimer(Timer.Global.INTERVAL.name()).reset();
            }
        };
    }

    protected ChangeListener resetStrollBehaviour(){
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StandUp.getInstance().getTimeKeeper().getTimer(Timer.Global.STROLL.name()).reset();
            }
        };
    }

    protected ChangeListener stopIntervalBehaviour(){
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                StandUp.getInstance().getTimeKeeper().getTimer(Timer.Global.INTERVAL.name()).stop();
            }
        };
    }

    protected ChangeListener backBehaviour(){
        return new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cWorldRenderer.setScreen(new HomeScreen(cWorldRenderer));
            }
        };
    }

    protected TextButton createButon(String label){
        TextButton button = cGameSkin.generateDefaultMenuButton(label);
        cTable.row().expandY();
        cTable.add(button);
        return button;
    }
}

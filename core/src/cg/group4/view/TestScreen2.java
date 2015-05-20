package cg.group4.view;

import cg.group4.game_logic.StandUp;
import cg.group4.util.camera.Skinner;
import cg.group4.util.camera.WorldRenderer;
import cg.group4.util.timer.TimeKeeper;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class TestScreen2 extends ScreenLogic{
    Table table;
    TextButton strollButton, settingsButton;
    Label title, timer;

    public TestScreen2(final WorldRenderer worldRenderer){
        super(worldRenderer);
        table = new Table();
        table.center();
        table.debugAll();
        table.setFillParent(true);

        title = new Label("Super StartFish Mania", Skinner.getInstance().get("default_labelStyle", Label.LabelStyle.class));
        table.row().expandY();
        table.add(title);

        timer = new Label("306", Skinner.getInstance().get("default_labelStyle", Label.LabelStyle.class));
        StandUp.getInstance().getTimeKeeper().getTimer(Timer.Global.INTERVAL.name()).subscribe(new TimerTask() {
            @Override
            public void onTick(int seconds) {
                timer.setText(Integer.toString(seconds));
            }

            @Override
            public void onStart(int seconds) {

            }

            @Override
            public void onStop() {

            }
        });
        table.row().expandY();
        table.add(timer);

        strollButton = Skinner.getInstance().generateDefaultMenuButton("Stroll");
        strollButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                worldRenderer.setScreen(new TestScreen(worldRenderer));
            }
        });
        table.row().expandY();
        table.add(strollButton);

        settingsButton = Skinner.getInstance().generateDefaultMenuButton("Settings");
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                worldRenderer.setScreen(new TestScreen(worldRenderer));
            }
        });
        table.row().expandY();
        table.add(settingsButton);


        worldRenderer.setActor(table);
    }

}

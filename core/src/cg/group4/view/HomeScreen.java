package cg.group4.view;

import cg.group4.game_logic.StandUp;
import cg.group4.util.camera.GameSkin;
import cg.group4.util.camera.WorldRenderer;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;


public class HomeScreen extends ScreenLogic{
    Table cTable;
    TextButton cStrollButton, cSettingsButton;
    Label title, timer;

    public HomeScreen(final WorldRenderer worldRenderer){
        super(worldRenderer);
        cTable = new Table();
        cTable.debugAll();
        cTable.setFillParent(true);

        title = new Label("Super StartFish Mania", StandUp.getInstance().getGameSkin().get("default_labelStyle", Label.LabelStyle.class));
        cTable.row().expandY();
        cTable.add(title);

        timer = new Label("3600", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
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
        cTable.row().expandY();
        cTable.add(timer);

        cStrollButton = cGameSkin.generateDefaultMenuButton("Stroll");
        cStrollButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                worldRenderer.setScreen(new TestScreen(worldRenderer));
            }
        });
        cTable.row().expandY();
        cTable.add(cStrollButton);

        cSettingsButton = cGameSkin.generateDefaultMenuButton("Settings");
        cSettingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                worldRenderer.setScreen(new SettingsScreen(worldRenderer));
            }
        });
        cTable.row().expandY();
        cTable.add(cSettingsButton);


        worldRenderer.setActor(cTable);
    }

}

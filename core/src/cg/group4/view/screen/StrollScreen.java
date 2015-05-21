package cg.group4.view.screen;

import cg.group4.game_logic.StandUp;
import cg.group4.util.timer.Timer;
import cg.group4.util.timer.TimerTask;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.WorldRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class StrollScreen extends ScreenLogic {
    protected Label cTimeRemaining, cText;
    protected Table cTable;

	public StrollScreen(WorldRenderer worldRenderer) {
		super(worldRenderer);
        cTable = new Table();
        cTable.setFillParent(true);
        cTable.row().expandY();
        cTimeRemaining = new Label("300", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        StandUp.getInstance().getTimeKeeper().getTimer(Timer.Global.STROLL.name())
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


        setAsActiveScreen();
	}

	@Override
	public void setAsActiveScreen() {
		// TODO Auto-generated method stub
		cWorldRenderer.setActor(cTable);
	}
}

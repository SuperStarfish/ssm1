package cg.group4.stroll;

import cg.group4.view.RewardScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Window.WindowStyle;


public class Stroll {
	
	protected final float cEventChance = 0.2f;
	protected int cEventsCompleted;
	protected Stage cStrollStage;
	
	public Stroll(Stage strollStage) {
		cEventsCompleted = 0;
		cStrollStage = strollStage;
		//this.onComplete();
		timerUpdate();
		//INSERT TIMER THAT COUNTS DOWN
	}
	
	public void timerUpdate() {
		WindowStyle w = new WindowStyle();
		w.titleFont = new BitmapFont();
		Dialog d = new Dialog("An event occured.", w);
		d.button("Accept");
		cStrollStage.addActor(d);
	}

	public void increaseEventsCompleted() {
		cEventsCompleted++;
	}
	
	public void onComplete() {
		((Game)Gdx.app.getApplicationListener()).setScreen(new RewardScreen(cEventsCompleted));
	}
}

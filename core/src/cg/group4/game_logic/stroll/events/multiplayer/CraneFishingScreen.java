package cg.group4.game_logic.stroll.events.multiplayer;

import cg.group4.game_logic.StandUp;
import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.ScreenStore;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class CraneFishingScreen extends ScreenLogic {
	
	protected WidgetGroup cContainer;
	
	public CraneFishingScreen() {
		cContainer = new WidgetGroup();
	}

	@Override
	protected WidgetGroup createWidgetGroup() {
		return cContainer;
	}

	@Override
	protected void rebuildWidgetGroup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected String setPreviousScreenName() {
		// TODO Auto-generated method stub
		return null;
	}
}

package cg.group4.view.screen;

import cg.group4.game_logic.StandUp;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;
import cg.group4.view.screen_mechanics.ScreenLogic;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MultiplayerHostScreen extends ScreenLogic {
	
	protected Table cTable;
	
	protected TextButton cBack;
	
	protected Label cInfo, cCode;
	
	public MultiplayerHostScreen() {
		cTable = new Table();
		cBack = cGameSkin.generateDefaultMenuButton("Back");
		cInfo = cGameSkin.generateDefaultLabel("Waiting for other player to connect. \n"
				 + "Let the other player fill in the code below and press \"Join\". \n"
				 + "To cancel, press \"Back\" \n");
		cCode = cGameSkin.generateDefaultLabel("Generating code...");
	}

	@Override
	protected String setPreviousScreenName() {
		return "Stroll";
	}

	@Override
	protected void rebuildWidgetGroup() {
		cBack.setStyle(cGameSkin.getDefaultTextButtonStyle());
		cInfo.setStyle(cGameSkin.getDefaultLabelStyle());
		cCode.setStyle(cGameSkin.getDefaultLabelStyle());
	}

	@Override
	protected WidgetGroup createWidgetGroup() {
		backButtonClicked();
		fillTable();
		startMultiplayerEvent();
		return cTable;
	}
	
	protected void fillTable() {
		cTable.setFillParent(true);
		cTable.add(cInfo).expand();
		cTable.row();
		cTable.add(cCode).expand();
		cTable.row();
		cTable.add(cBack).expand();
	}
	
	protected void startMultiplayerEvent() {
		StandUp.getInstance().getStroll().startMultiPlayerEvent(new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                cCode.setText("Code: " + Integer.toString((Integer) response.getData()));
            }
        });
	}
	
	protected void backButtonClicked() {
		cBack.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				cScreenStore.setScreen(getPreviousScreenName());
			}
		});
	}

}

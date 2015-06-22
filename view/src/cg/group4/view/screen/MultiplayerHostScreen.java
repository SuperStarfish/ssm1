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

/**
 * Screen to e displayed when hosting a multiplayer event.
 */
public class MultiplayerHostScreen extends ScreenLogic {
	
	 /**
     * Table that all the elements are added to.
     */
	protected Table cTable;
	
	/**
	 * Button to go back to the previous screen.
	 */
	protected TextButton cBack;
	
	/**
	 * Labels that display the information and the generated code.
	 */
	protected Label cInfo, cCode;
	
	/**
	 * Sets the default values for the labels and the buttons.
	 */
	public MultiplayerHostScreen() {
		cTable = new Table();
		cBack = cGameSkin.generateDefaultMenuButton("Back");
		cInfo = cGameSkin.generateDefaultLabel("Waiting for other player to connect. \n"
				 + "Let the other player fill in the code \n below and press \"Join\". \n"
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
	
	/**
	 * Fills the table of the screen.
	 */
	protected void fillTable() {
		cTable.setFillParent(true);
		cTable.add(cInfo).expand();
		cTable.row();
		cTable.add(cCode).expand();
		cTable.row();
		cTable.add(cBack).expand();
	}
	
	/**
	 * Generates the code to be used for a multiplayer event to start.
	 */
	protected void startMultiplayerEvent() {
		StandUp.getInstance().getStroll().startMultiPlayerEvent(new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                cCode.setText("Code: " + Integer.toString((Integer) response.getData()));
            }
        });
	}
	
	/**
	 * Fires when the back button is clicked.
	 */
	protected void backButtonClicked() {
		cBack.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				cScreenStore.setScreen(getPreviousScreenName());
			}
		});
	}

}

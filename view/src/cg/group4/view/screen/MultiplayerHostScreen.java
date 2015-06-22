package cg.group4.view.screen;

import cg.group4.game_logic.StandUp;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;
import cg.group4.view.screen_mechanics.ScreenLogic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.Observable;
import java.util.Observer;

/**
 * Screen to e displayed when hosting a multiplayer event.
 */
public class MultiplayerHostScreen extends ScreenLogic implements InputProcessor {
	
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
	 * Inputprocessor of the strollevent.
	 */
	protected InputProcessor cProcessor;
	/**
	 * Sets the default values for the labels and the buttons.
	 */
	public MultiplayerHostScreen() {
		cTable = new Table();
		cBack = cGameSkin.generateDefaultMenuButton("Back");
		cInfo = cGameSkin.generateDefaultLabel("Waiting for other player to connect. "
				+ "Let the other player fill in the code below and press 'Join'. \nTo cancel, press 'Back'.");
		cInfo.setWrap(true);
		cCode = cGameSkin.generateDefaultLabel("Generating code...");
		cProcessor = Gdx.input.getInputProcessor();
		StandUp.getInstance().getStroll().getNewEventSubject().addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                dispose();
            }
        });
		if (cProcessor instanceof InputMultiplexer) {
			((InputMultiplexer) cProcessor).addProcessor(this);
		}
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
		cTable.add(cInfo).expand().fill();
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
                dispose();
			}
		});
	}

	public void dispose() {
		final InputProcessor myself = this;
		if (cProcessor instanceof InputMultiplexer) {
			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					((InputMultiplexer) cProcessor).removeProcessor(myself);
				}
			});
		}
	}

	@Override
	public final boolean keyDown(final int keycode) {
		if (keycode == Input.Keys.BACK || keycode == Input.Keys.F1) {
            StandUp.getInstance().getStroll().cancelEvent();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}

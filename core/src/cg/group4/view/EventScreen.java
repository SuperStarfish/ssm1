package cg.group4.view;

import cg.group4.util.camera.WorldRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class EventScreen extends ScreenLogic {
	Label cTaskToPerform;
	Container cContainer;

	public EventScreen(WorldRenderer worldRenderer) {
		super(worldRenderer);
		cContainer = new Container();
		cContainer.setFillParent(true);
		cTaskToPerform = new Label("TASK:", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));

		cContainer.setActor(cTaskToPerform);
		cWorldRenderer.setActor(cContainer);
		
		setAsActiveScreen();
	}

    public Label getLabel(){
        return cTaskToPerform;
    }

	@Override
	public void setAsActiveScreen() {
		// TODO Auto-generated method stub
		cWorldRenderer.setActor(cContainer);
	}
}

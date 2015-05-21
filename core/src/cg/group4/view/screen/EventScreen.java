package cg.group4.view.screen;

import cg.group4.view.screen_mechanics.ScreenLogic;
import cg.group4.view.screen_mechanics.WorldRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

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

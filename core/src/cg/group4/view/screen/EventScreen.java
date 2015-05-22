package cg.group4.view.screen;

import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class EventScreen extends ScreenLogic {

	/**
	 * Label with the text of this event
	 */
	Label cTaskToPerform;

	@Override
	protected WidgetGroup createWidgetGroup() {
		Container cContainer = new Container();
		cContainer.setFillParent(true);
		cTaskToPerform = new Label("TASK:", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));

		cContainer.setActor(cTaskToPerform);
		return cContainer;
	}

	/**
	 * Returns the label of the screen.
	 * @return The label.
	 */
	public Label getLabel(){
        return cTaskToPerform;
    }

}

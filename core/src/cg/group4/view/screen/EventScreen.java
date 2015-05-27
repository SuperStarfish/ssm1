package cg.group4.view.screen;

import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

public class EventScreen extends ScreenLogic {

    /**
     * Label with the text of this event
     */
    protected Label cTaskToPerform;

    @Override
    protected WidgetGroup createWidgetGroup() {
        Container<Label> cContainer = new Container<Label>();
        cContainer.setFillParent(true);
        cTaskToPerform = new Label("TASK:", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));

        cContainer.setActor(cTaskToPerform);
        return cContainer;
    }

    @Override
    protected void rebuildWidgetGroup() {
        cTaskToPerform.setStyle(cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
    }

    /**
     * Returns the label of the screen.
     *
     * @return The label.
     */
    public Label getLabel() {
        return cTaskToPerform;
    }

    @Override
    protected String setPreviousScreenName() {
        return "Stroll";
    }
}

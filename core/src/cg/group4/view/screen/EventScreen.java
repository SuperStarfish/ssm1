package cg.group4.view.screen;

import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

/**
 * Screen to be displayed during an event.
 */
public final class EventScreen extends ScreenLogic {

    /**
     * Label with the text of this event.
     */
    protected Label cTaskToPerform;

    public EventScreen() {
        cTaskToPerform = new Label("TASK:", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
    }

    @Override
    protected WidgetGroup createWidgetGroup() {
        Container<Label> cContainer = new Container<Label>();
        cContainer.setFillParent(true);

        cContainer.setActor(cTaskToPerform);
        return cContainer;
    }

    /**
     * Returns the label of the screen.
     *
     * @return The label.
     */
    public Label getLabel() {
        return cTaskToPerform;
    }

}

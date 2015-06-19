package cg.group4.view.screen;

import cg.group4.game_logic.stroll.events.StrollEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

/**
 * Screen to be displayed during an event.
 */
public final class TextEventScreen extends EventScreen {

    /**
     * Label with the text of this event.
     */
    protected Label cTaskToPerform;

    /**
     * Creates a new Text Event.
     * @param event The event logic.
     */
    public TextEventScreen(final StrollEvent event) {
        super(event);
    }

    @Override
    protected void init() {
        cTaskToPerform = cGameSkin.generateDefaultLabel("");
    }

    @Override
    protected void onEventChange(Object updatedData) {
        cTaskToPerform.setText((String) updatedData);
    }

    @Override
    protected WidgetGroup createWidgetGroup() {

        Container<Label> cContainer = new Container<Label>();
        cContainer.setFillParent(true);

        cContainer.setActor(cTaskToPerform);
        return cContainer;
    }

    @Override
    protected String setPreviousScreenName() {
        return "Stroll";
    }

    @Override
    protected void rebuildWidgetGroup() {
        cTaskToPerform.setStyle(cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
    }
}

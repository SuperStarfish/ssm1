package cg.group4.view.screen;

import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import java.util.Observable;
import java.util.Observer;

/**
 * Screen to be displayed during an event.
 */
public final class TextEventScreen extends EventScreen {

    /**
     * Label with the text of this event.
     */
    protected Label cTaskToPerform;

    public TextEventScreen(final StrollEvent event) {
        super(event);
    }

    @Override
    void init() {
        cTaskToPerform = cGameSkin.generateDefaultLabel("");
    }

    @Override
    void onEventChange(Object updatedData) {
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
    protected void rebuildWidgetGroup() {
        cTaskToPerform.setStyle(cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
    }

    @Override
    protected String setPreviousScreenName() {
        return "Stroll";
    }
}

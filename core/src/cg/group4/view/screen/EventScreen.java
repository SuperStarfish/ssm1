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
public final class EventScreen extends ScreenLogic {

    /**
     * Label with the text of this event.
     */
    protected Label cTaskToPerform;

    protected Observer cLabelObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            cTaskToPerform.setText(arg.toString());
        }
    };

    /**
     * Creates an event screen to display the data from an event.
     * @param event The event belonging to this screen.
     */
    public EventScreen(StrollEvent event) {
        cTaskToPerform = new Label("Event", cGameSkin.get("default_labelStyle", Label.LabelStyle.class));
        event.getLabelSubject().addObserver(cLabelObserver);
        event.start();
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

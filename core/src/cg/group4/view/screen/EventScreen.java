package cg.group4.view.screen;

import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import java.util.Observable;
import java.util.Observer;

public abstract class EventScreen extends ScreenLogic {
    /**
     * Observes label changes.
     */
    protected Observer cEventObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            onEventChange(arg);
        }
    };

    public EventScreen(final StrollEvent eventLogic) {
        eventLogic.getEventChangeSubject().addObserver(cEventObserver);
        eventLogic.start();
    }

    abstract void onEventChange(Object updatedData);

    @Override
    protected WidgetGroup createWidgetGroup() {
        return null;
    }

    @Override
    protected String setPreviousScreenName() {
        return "Stroll";
    }
}

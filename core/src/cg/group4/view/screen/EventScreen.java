package cg.group4.view.screen;

import cg.group4.game_logic.stroll.events.StrollEvent;
import cg.group4.view.screen_mechanics.ScreenLogic;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;

import java.util.Observable;
import java.util.Observer;

/**
 * Event Screens extend the default ScreenLogic to add an observer where Objects are received that change the state
 * of the screen.
 */
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

    /**
     * Creates a new event and adds an observer to the event logic.
     * @param eventLogic The event logic.
     */
    public EventScreen(final StrollEvent eventLogic) {
        eventLogic.getEventChangeSubject().addObserver(cEventObserver);
        init();
        eventLogic.start();
    }

    /**
     * This code is run before the start. Initialize anything needed for the event here.
     */
    protected abstract void init();

    /**
     * Is called every time there is a change in the event logic.
     *
     * @param updatedData Object containing all the updated data.
     */
    protected abstract void onEventChange(Object updatedData);

    @Override
    protected WidgetGroup createWidgetGroup() {
        return null;
    }

    @Override
    protected String setPreviousScreenName() {
        return "Stroll";
    }
}

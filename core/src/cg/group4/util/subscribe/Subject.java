package cg.group4.util.subscribe;

import java.util.Observable;

/**
 * Subject that can be subscribed to for changes.
 *
 * @author Benjamin Los
 */
public class Subject extends Observable {

    /**
     * Notifies the subscribers.
     */
    public void update(Object item) {
        setChanged();
        notifyObservers(item);
    }
}

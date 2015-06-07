package cg.group4.collection;

import cg.group4.collection.collectibles.Collectible;
import cg.group4.subscribe.Subject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Collection which can hold collectibles.
 * A collectible exists out of an shape and colour.
 * Only one of each collectibles can exist in one collection.
 */
public class Collection extends HashSet<Collectible> implements Serializable {

    /**
     * Identifier of the collection.
     */
    protected String cId;

    /**
     * Subject that gets notified when something is added to the HashMap.
     */
    protected Subject cChangeAddSubject;

    /**
     * Subject that gets notified when something is removed form the HashMap.
     */
    protected Subject cChangeRemoveSubject;

    /**
     * Constructs a HashSet collection to store collectibles gained by the player.
     * @param id Identifier of the collection.
     */
	public Collection(final String id) {
        super();
        cId = id;
        cChangeAddSubject = new Subject();
        cChangeRemoveSubject = new Subject();
    }

    /**
     * String representation of collection.
     * @return String representation
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Collection<");

        int counter = 0;
        for (Collectible c : this) {
            counter++;
            sb.append(c.toString());
            if (counter < this.size()) {
                sb.append(", ");
            }
        }
        sb.append(">");
        return sb.toString();
    }

    /**
     * Returns the identifier of this collection.
     * @return identifier
     */
    public String getId() {
        return cId;
    }

    /**
     * Sorts a list based on a given comparator.
     *
     * @param comparator The comparator.
     * @return The sorted list.
     */
    public ArrayList<Collectible> sort(Comparator<Collectible> comparator) {
        ArrayList<Collectible> list = new ArrayList<>(this);
        Collections.sort(list, comparator);
        return list;
    }

    @Override
    public boolean add(Collectible collectible) {
        boolean result = super.add(collectible);
        Collection collection = new Collection(cId);
        collection.add(collectible);
        cChangeAddSubject.update(collection);
        return result;
    }

    @Override
    public boolean remove(Object o) {
        boolean result = super.remove(o);
        Collection collection = new Collection(cId);
        collection.add((Collectible) o);
        cChangeRemoveSubject.update(collection);
        return result;
    }

    @Override
    public boolean removeAll(java.util.Collection<?> c) {
        boolean result = super.removeAll(c);
        cChangeRemoveSubject.update(c);
        return result;
    }

    @Override
    public boolean addAll(java.util.Collection<? extends Collectible> c) {
        boolean result = super.addAll(c);
        cChangeAddSubject.update(c);
        return result;
    }

    /**
     * Getter for the change add subject. Gets notified when something is added to the HashMap.
     *
     * @return The change add subject.
     */
    public Subject getChangeAddSubject() {
        return cChangeAddSubject;
    }

    /**
     * Getter for the change remove subject. Gets notified when something is remove from the HashMap.
     *
     * @return The change remove subject.
     */
    public Subject getChangeRemoveSubject() {
        return cChangeRemoveSubject;
    }
}

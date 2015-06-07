package cg.group4.collection;

import cg.group4.collection.collectibles.Collectible;

import java.io.Serializable;
import java.util.*;

/**
 * Collection which can hold collectibles.
 * A collectible exists out of an shape and colour.
 * Only one of each collectibles can exist in one collection.
 */
public class Collection extends HashSet<Collectible> implements Observer, Serializable {

    /**
     * Identifier of the collection.
     */
    protected String cId;

    /**
     * Constructs a HashSet collection to store collectibles gained by the player.
     * @param id Identifier of the collection.
     */
	public Collection(final String id) {
        super();
        cId = id;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        if (arg instanceof Collectible) {
            this.add((Collectible) arg);
        }
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
        ArrayList<Collectible> list = new ArrayList<Collectible>(this);
        Collections.sort(list, comparator);
        return list;
    }
}

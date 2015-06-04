package cg.group4.rewards;

import cg.group4.rewards.collectibles.Collectible;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

/**
 * Collection which can hold collectibles.
 * A collectible exists out of an shape and colour.
 * Only one of each collectibles can exist in one collection.
 */
public class Collection extends HashSet<Collectible> implements Observer, Serializable {

    /**
     * Constructs a HashSet collection to store collectibles gained by the player.
     */
	public Collection() {
        super();
    }

    @Override
    public void update(Observable o, Object arg) {
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

}

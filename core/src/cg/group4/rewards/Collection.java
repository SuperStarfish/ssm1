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
public class Collection extends HashSet<Collectible> implements Serializable, Observer {

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
}

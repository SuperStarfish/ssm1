package cg.group4.rewards;

import cg.group4.rewards.collectibles.Collectible;

import java.util.HashSet;

/**
 * Collection which can hold collectibles.
 * A collectible exists out of an shape and colour.
 * Only one of each collectibles can exist in one collection.
 */
public class Collection {
	
	protected HashSet<Collectible> cCollection;
	
	public Collection() {
		cCollection = new HashSet<Collectible>();
	}
	
	public HashSet<Collectible> getCollection() {
		return cCollection;
	}
	
	public void add(Collectible c) {
		cCollection.add(c);
	}
	
	public int size() {
		return cCollection.size();
	}

}

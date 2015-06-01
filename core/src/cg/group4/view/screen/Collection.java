package cg.group4.view.screen;

import java.util.HashSet;

import cg.group4.rewards.collectibles.Collectible;

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

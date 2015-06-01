package cg.group4.rewards.collectibles.collectibleSorters;

import java.util.ArrayList;
import java.util.HashSet;

import cg.group4.rewards.collectibles.Collectible;

public interface CollectibleSorter {
	
	abstract ArrayList<Collectible> sortCollectibles(HashSet<Collectible> set);

}

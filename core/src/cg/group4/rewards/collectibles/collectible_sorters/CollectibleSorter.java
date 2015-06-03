package cg.group4.rewards.collectibles.collectible_sorters;

import cg.group4.rewards.collectibles.Collectible;

import java.util.ArrayList;
import java.util.HashSet;

public interface CollectibleSorter {
	
	abstract ArrayList<Collectible> sortCollectibles(HashSet<Collectible> set);

}

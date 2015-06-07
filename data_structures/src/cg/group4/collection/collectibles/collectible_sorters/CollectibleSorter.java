package cg.group4.collection.collectibles.collectible_sorters;

import cg.group4.collection.collectibles.Collectible;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Interface that sorts the collectibles.
 */
public interface CollectibleSorter {

	/**
	 * Method that sorts the collectibles.
	 * @param set HashSet containing the collectibles to be sorted.
	 * @return ArrayList of sorted collectibles.
	 */
	abstract ArrayList<Collectible> sortCollectibles(HashSet<Collectible> set);

}

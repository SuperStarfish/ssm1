package cg.group4.rewards.collectibles.collectible_sorters;

import java.util.ArrayList;
import java.util.HashSet;

import cg.group4.rewards.collectibles.Collectible;

/**
 * Object that sorts a HashSet of collectibles based on their rarity.
 * (From most rare to least rare)
 */
public class SortByRarity implements CollectibleSorter {

	@Override
	public ArrayList<Collectible> sortCollectibles(final HashSet<Collectible> set) {
		ArrayList<Collectible> sortedList = new ArrayList<Collectible>(set.size());
		
		for (Collectible c : set) {
			if (sortedList.isEmpty()) {
				sortedList.add(c);
			}
			else {
				sortedList.add(findPosition(c, sortedList), c);
			}
		}
		return sortedList;
	}
	
	protected int findPosition(Collectible c, ArrayList<Collectible> list) {
		for(int i = 0; i < list.size(); i++) {
			if(c.getRarity() > list.get(i).getRarity()) {
				return i;
			}
		}
		return list.size();
	}

}

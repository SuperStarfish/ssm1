package cg.group4.collection.collectibles.collectible_sorters;

import cg.group4.collection.collectibles.Collectible;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

/**
 * Object that sorts a HashSet of collectibles based on their rarity.
 * (From most rare to least rare)
 */
public class SortByRarity implements CollectibleSorter {

    @Override
    public ArrayList<Collectible> sortCollectibles(final HashSet<Collectible> set) {
        ArrayList<Collectible> list = new ArrayList<Collectible>(set);

        Collections.sort(list, new Comparator<Collectible>() {
            @Override
            public int compare(final Collectible o1, final Collectible o2) {
                if (o1.getRarity() < o2.getRarity()) {
                    return 1;
                }
                if (o1.getRarity() == o2.getRarity()) {
                    return 0;
                }
                return -1;
            }
        });

        return list;
    }

}

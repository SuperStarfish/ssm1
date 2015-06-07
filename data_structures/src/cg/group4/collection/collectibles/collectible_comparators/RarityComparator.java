package cg.group4.collection.collectibles.collectible_comparators;

import cg.group4.collection.collectibles.Collectible;

import java.util.Comparator;

/**
 * Object that sorts a HashSet of collectibles based on their rarity.
 * (From most rare to least rare)
 */
public class RarityComparator implements Comparator<Collectible> {

    @Override
    public int compare(Collectible o1, Collectible o2) {
        if (o1.getRarity() < o2.getRarity()) {
            return 1;
        }
        if (o1.getRarity() == o2.getRarity()) {
            return 0;
        }
        return -1;
    }
}

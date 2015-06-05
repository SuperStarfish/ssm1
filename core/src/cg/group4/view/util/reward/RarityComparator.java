package cg.group4.view.util.reward;

import cg.group4.rewards.collectibles.Collectible;

import java.util.Comparator;

/**
 * Object that compares collectibles to be sorted.
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

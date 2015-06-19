package cg.group4.data_structures.collection.collectibles.collectible_comparators;

import cg.group4.data_structures.collection.collectibles.Collectible;

import java.util.Comparator;

/**
 * Compares two collectibles on hue.
 */
public class HueComparator implements Comparator<Collectible> {

    @Override
    public int compare(final Collectible o1, final Collectible o2) {
        if (o1 == null || o2 == null) { //In case there is only one collectible.
            return 0;
        }

        if (o1.getHue() < o2.getHue()) {
            return 1;
        }
        if (o1.getHue() == o2.getHue()) {
            return 0;
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Hue";
    }
}

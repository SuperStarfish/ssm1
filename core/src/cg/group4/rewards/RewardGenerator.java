package cg.group4.rewards;

import cg.group4.rewards.collectibles.Collectible;
import cg.group4.rewards.collectibles.CollectibleFactory;

import java.util.Random;

/**
 * Objects that generates the rewards.
 *
 * @author Jean de Leeuw
 */
public class RewardGenerator {
    /**
     * Object that creates collectibles.
     */
    protected final CollectibleFactory cCollectibleFactory;
    /**
     * Generates random numbers used in the generation of the rewards.
     */
    protected Random cRNG;

    /**
     * Constructs a new reward generator.
     */
    public RewardGenerator() {
        cRNG = new Random();
        cCollectibleFactory = new CollectibleFactory();
    }

    /**
     * Generates a collectible based on the score that was earned in the event.
     * The higher the score, the more chance on a more rare collectible.
     *
     * @param eventScore score earned during the event.
     * @return Collectible object.
     */
    public final Collectible generateCollectible(final int eventScore) {
        Collectible mostValuable = null;
        double mostRare = -Double.MAX_VALUE;

        for (int i = 0; i < eventScore; i++) {
            Collectible c = generateOneCollectible();
            if (c.getRarity() > mostRare) {
                mostValuable = c;
                mostRare = c.getRarity();
            }
        }
        return mostValuable;
    }

    /**
     * Helper method that should not be called outside of this class.
     * Generates a random collectible and return this created collectible.
     *
     * @return Collectible object.
     */
    protected final Collectible generateOneCollectible() {
        String[] collectibleList = cCollectibleFactory.getCollectiblesList();
        int nr = cRNG.nextInt(collectibleList.length);
        double hue = rewardFunction(cRNG.nextFloat());
        return cCollectibleFactory.generateCollectible(collectibleList[nr], (float) hue);
    }

    /**
     * Helper method that should not be called outside of this class.
     * Applies a function to the input.
     * <p/>
     * The reward function now is specified as: f(x) = x^4.
     *
     * @param x Input
     * @return Result after applying the function to the input
     */
    protected final double rewardFunction(final double x) {
        final int exponent = 4;
        return Math.pow(x, exponent);
    }
}

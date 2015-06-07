package cg.group4.game_logic;

import cg.group4.rewards.Collection;
import cg.group4.rewards.RewardGenerator;

/**
 * Class containing user specific data.
 */
public class Player {

    /**
     * The id of the player.
     */
    protected String cId;
    /**
     * The collection of rewards of the player.
     */
    protected Collection cCollection;

    /**
     * Creates a new player.
     */
    public Player() {
        cId = "local";
        cCollection = new Collection(cId);

        RewardGenerator gen = new RewardGenerator();
        for (int i = 0; i < 100; i++) {
            cCollection.add(gen.generateCollectible(1));
        }
    }

    /**
     * Getter for the players collection.
     *
     * @return The players collection.
     */
    public Collection getCollection() {
        return cCollection;
    }
}

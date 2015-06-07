package cg.group4.game_logic;


import cg.group4.collection.Collection;

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
        cCollection = new Collection(cId);
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

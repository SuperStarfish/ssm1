package cg.group4.game_logic;

import cg.group4.client.Client;
import cg.group4.data_structures.PlayerData;
import cg.group4.data_structures.collection.Collection;

import java.util.Observable;
import java.util.Observer;

/**
 * Player class that contains the player data and maintains its validity.
 */
public class Player {

    /**
     * The players data.
     */
    protected PlayerData cPlayerData;

    /**
     * Observer additions made to the collection.
     */
    protected Observer cAddChangeObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            Client.getInstance().updateCollection((Collection) arg);
        }
    };

    /**
     * Constructs a player object.
     */
    public Player() {
        cPlayerData = Client.getInstance().getPlayerData();
        cPlayerData.getCollection().getChangeAddSubject().addObserver(cAddChangeObserver);
    }

    /**
     * Retrieves the collection from the players data.
     *
     * @return The collection.
     */
    public Collection getCollection() {
        return cPlayerData.getCollection();
    }

    /**
     * Retrieves the username from the players data.
     *
     * @return The username.
     */
    public String getUsername() {
        return cPlayerData.getUsername();
    }

    /**
     * Retrieves the id from the players data.
     *
     * @return The id.
     */
    public String getId() {
        return cPlayerData.getId();
    }
}

package cg.group4.game_logic;

import cg.group4.PlayerData;
import cg.group4.client.Client;
import cg.group4.collection.Collection;

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
        public void update(Observable o, Object arg) {
            Client.getInstance().updateCollection((Collection) arg);
        }
    };

    public Player() {
        cPlayerData = Client.getInstance().getPlayerData();
        cPlayerData.getCollection().getChangeAddSubject().addObserver(cAddChangeObserver);
    }

    public Collection getCollection() {
        return cPlayerData.getCollection();
    }


    public String getUsername() {
        return cPlayerData.getUsername();
    }

    public String getId() {
        return cPlayerData.getId();
    }
}

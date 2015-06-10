package cg.group4.game_logic;

import cg.group4.client.Client;
import cg.group4.data_structures.PlayerData;
import cg.group4.data_structures.collection.Collection;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;

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
            Collection collection = (Collection) arg;
            collection.setGroupId(cPlayerData.getId());
            Client.getLocalInstance().addCollection(collection, null);
        }
    };

    /**
     * Constructs a player object.
     */
    public Player() {
        update();
    }

    public void update() {
        final Client localStorage = Client.getLocalInstance();
        if (localStorage.isConnected()) {
            localStorage.getPlayerData(new ResponseHandler() {
                @Override
                public void handleResponse(Response response) {
                    PlayerData playerData;
                    if (response.isSuccess()) {
                        playerData = (PlayerData) response.getData();
                    } else {
                        playerData = new PlayerData(localStorage.getUserID());
                    }
                    if (playerData.getUsername() == null) {
                        playerData.setUsername("Unknown");
                    }
                    cPlayerData = playerData;
                }
            });
        } else {
            cPlayerData = new PlayerData(localStorage.getUserID());
        }

        cPlayerData.getCollection().getChangeAddSubject().addObserver(cAddChangeObserver);
    }

    public PlayerData getPlayerData() {
        return cPlayerData;
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
        String username = cPlayerData.getUsername();
        if (username == null) {
            return "Unknown";
        }
        return username;
    }

    /**
     * Retrieves the id from the players data.
     *
     * @return The id.
     */
    public String getId() {
        return cPlayerData.getId();
    }

    public String getGroupId() {
        return cPlayerData.getGroupId();
    }

    /**
     * 
     * @param groupId
     */
    public void setPlayerDataGroupId(int groupId) {
        cPlayerData.setGroupId(Integer.toString(groupId));
    }
}

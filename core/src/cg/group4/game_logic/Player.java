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
     * The collection of the player.
     */
    protected Collection cCollection;

    /**
     * Observer additions made to the collection.
     */
    protected Observer cAddChangeObserver = new Observer() {
        @Override
        public void update(final Observable o, final Object arg) {
            Collection collection = (Collection) arg;
            collection.setId(cPlayerData.getId());
            Client.getInstance().updatePlayerCollection(collection, null);
            updatePlayerCollection();
        }
    };

    /**
     * Observers the remote connection.
     */
    protected Observer cRemoteConnectionObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            if ((boolean) arg) {
                updatePlayerData();
                updatePlayerCollection();
            }
        }
    };

    /**
     * Constructs a player object.
     */
    public Player() {
        cPlayerData = new PlayerData(Client.getInstance().getUserID());
        updatePlayerData();
        cCollection = new Collection(Client.getInstance().getUserID());
        updatePlayerCollection();
        Client.getInstance().getRemoteChangeSubject().addObserver(cRemoteConnectionObserver);
    }

    /**
     * Fetches the new player data.
     */
    public void updatePlayerData() {
        final Client client = Client.getInstance();
        client.getPlayerData(new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                if (response.isSuccess()) {
                    cPlayerData = (PlayerData) response.getData();
                }
                client.updateRemoteUsername(cPlayerData.toString(), null);
                if (cPlayerData.toString() == null) {
                    cPlayerData.setUsername("Unknown");
                }
            }
        });
        client.getGroupId(new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                if (response.isSuccess()) {
                    cPlayerData.setGroupId((String) response.getData());
                }
            }
        });
    }

    /**
     * Fetches the new player collection.
     */
    public void updatePlayerCollection() {
        final Client client = Client.getInstance();
        client.getPlayerCollection(new ResponseHandler() {
            @Override
            public void handleResponse(Response response) {
                if (response.isSuccess()) {
                    cCollection = (Collection) response.getData();
                }
                cCollection.getChangeAddSubject().addObserver(cAddChangeObserver);
            }
        });
    }

    /**
     * Getter for the player data.
     *
     * @return The player data.
     */
    public PlayerData getPlayerData() {
        return cPlayerData;
    }

    /**
     * Gets the players collection.
     *
     * @return The players collection.
     */
    public Collection getCollection() {
        return cCollection;
    }


    /**
     * Sets the players collection.
     *
     * @param collection The players collection.
     */
    public void setCollection(final Collection collection) {
        cCollection = collection;
    }

    /**
     * Retrieves the username from the players data.
     *
     * @return The username.
     */
    public String getUsername() {
        String username = cPlayerData.toString();
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

    /**
     * Getter for group id.
     *
     * @return The id of the group that the player belongs to.
     */
    public String getGroupId() {
        return cPlayerData.getGroupId();
    }

    /**
     * Setter for group id.
     * @param groupId The id of the group that the player belongs to.
     */
    public void setGroupId(String groupId) {
        cPlayerData.setGroupId(groupId);
    }
}

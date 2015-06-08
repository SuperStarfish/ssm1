package cg.group4.client;

import cg.group4.PlayerData;
import cg.group4.client.connection.Connection;
import cg.group4.client.connection.LocalConnection;
import cg.group4.collection.Collection;
import cg.group4.database.query.RequestPlayerData;
import cg.group4.database.query.UpdateCollection;
import cg.group4.database.query.UpdatePlayerData;

/**
 * @author Jurgen van Schagen
 */
public final class Client {
    /**
     * Instance for Singleton.
     */
    protected static Client cInstance;

    /**
     * Gets the Singleton instance.
     * @return The Client.
     */
    public static Client getInstance() {
        if (cInstance == null) {
            cInstance = new Client();
        }
        return cInstance;
    }

    /**
     * The connection state with the server.
     */
    protected Connection cConnection;

    /**
     * The user id. Android Phone ID or Desktop name.
     */
    protected UserIDResolver cUserIDResolver;

    /**
     * The default IP to connect to.
     */
    protected final String defaultIP = "128.127.39.32";

    /**
     * The default port to connect to.
     */
    protected final int defaultPort = 56789;

    /**
     * Constructs a new Client and sets the state to unconnected.
     */
    public Client() {
        cConnection = new LocalConnection();
    }

    /**
     * Connects to the server. Behaviour depends on the state.
     */
    public void connectToServer() {
        cConnection = cConnection.connect(defaultIP, defaultPort);
    }

    /**
     * Closes the connection with the server. Behaviour depends on the state.
     */
    public void closeConnection() {
        cConnection = cConnection.disconnect();
    }

//    /**
//     * Updates the timers in the database. Behaviour depends on the state.
//     *
//     * @param timeStamp The timestamp from which to add the timer durations.
//     */
//    public void updateTimers(final long timeStamp) {
//        UserData data = new UserData();
//        data.setcId(cUserIDResolver.getID());
//        data.setcIntervalTimeStamp(timeStamp + Timer.Global.INTERVAL.getDuration());
//        data.setcStrollTimeStamp(timeStamp + Timer.Global.STROLL.getDuration());
//
//        cConnection.updateUserData(data);
//    }
//
    /**
     * Updates the username in the server. Behaviour depends on the state.
     * @param username The new username.
     * @return Successful or not.
     */
    public Boolean updatePlayer(final String username) {

        PlayerData playerData = new PlayerData(cUserIDResolver.getID());
        playerData.setUsername(username);

        return cConnection.send(new UpdatePlayerData(playerData)).isSuccess();
    }

    /**
     * Adds the collection to the server. Behaviour depends on the state.
     * @param collection The collection to add to the server.
     * @return Successful or not.
     */
    public boolean updateCollection(final Collection collection) {
        collection.setGroupId(cUserIDResolver.getID());
        return cConnection.send(new UpdateCollection(collection)).isSuccess();
    }

    /**
     * Gets the userdata from the server. Uses UserIDResolver to get the data. Behaviour depends on the state.
     * @return All the userdata.
     */
    public PlayerData getPlayerData() {
        return (PlayerData) cConnection.send(new RequestPlayerData(cUserIDResolver.getID())).getData();
    }

    /**
     * Sets the userid using the device ID.
     *
     * @param idResolver Tool used for getting the proper device ID.
     */
    public void setUserIDResolver(final UserIDResolver idResolver) {
        cUserIDResolver = idResolver;
    }

    /**
     * Returns if connected to the server or not.
     *
     * @return Is connected or not.
     */
    public boolean isConnected() {
        return cConnection.isConnected();
    }
}

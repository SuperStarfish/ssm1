package cg.group4.client;

import cg.group4.client.connection.Connection;
import cg.group4.client.connection.LocalConnection;
import cg.group4.data_structures.PlayerData;
import cg.group4.data_structures.collection.Collection;
import cg.group4.server.database.Response;
import cg.group4.server.database.query.*;
import cg.group4.util.IpResolver;

import java.net.UnknownHostException;

/**
 * @author Jurgen van Schagen
 */
public final class Client {
    /**
     * Instance for Singleton.
     */
    protected static Client cInstance;
    /**
     * The default IP to connect to.
     */

    protected final String cDefaultIp = "127.0.0.1";
    /**
     * The default port to connect to.
     */
    protected final int cDefaultPort = 56789;
    /**
     * The connection state with the server.
     */
    protected Connection cConnection;
    /**
     * The user id. Android Phone ID or Desktop name.
     */
    protected UserIDResolver cUserIDResolver;

    /**
     * Constructs a new Client and sets the state to unconnected.
     */
    public Client() {
        cConnection = new LocalConnection();
        cUserIDResolver = new DummyUserIdResolver();
    }

    /**
     * Gets the Singleton instance.
     *
     * @return The Client.
     */
    public static Client getInstance() {
        if (cInstance == null) {
            cInstance = new Client();
        }
        return cInstance;
    }

    /**
     * Connects to the server. Behaviour depends on the state.
     */
    public void connectToServer() {
        cConnection = cConnection.connect(cDefaultIp, cDefaultPort);
    }

    /**
     * column_1
     * Closes the connection with the server. Behaviour depends on the state.
     */
    public void closeConnection() {
        cConnection = cConnection.disconnect();
    }

    /**
     * Updates the stroll timers in the database.
     *
     * @param timeStamp The timestamp when the timer should end.
     * @return Whether the query succeeded.
     */
    public boolean updateStrollTimer(final Long timeStamp) {
        PlayerData playerData = new PlayerData(cUserIDResolver.getID());
        playerData.setStrollTimeStamp(timeStamp);

        return cConnection.send(new UpdatePlayerData(playerData)).isSuccess();
    }

    /**
     * Updates the interval timers in the database.
     *
     * @param timeStamp The timestamp when the timer should end.
     * @return Whether the query succeeded.
     */
    public boolean updateIntervalTimer(final Long timeStamp) {
        PlayerData playerData = new PlayerData(cUserIDResolver.getID());
        playerData.setIntervalTimeStamp(timeStamp);

        return cConnection.send(new UpdatePlayerData(playerData)).isSuccess();
    }

    /**
     * Updates the username in the server. Behaviour depends on the state.
     *
     * @param username The new username.
     * @return Successful or not.
     */
    public boolean updatePlayer(final String username) {
        PlayerData playerData = new PlayerData(cUserIDResolver.getID());
        playerData.setUsername(username);

        return cConnection.send(new UpdatePlayerData(playerData)).isSuccess();
    }

    /**
     * Adds the collection to the server. Behaviour depends on the state.
     *
     * @param collection The collection to add to the server.
     * @return Successful or not.
     */
    public boolean updateCollection(final Collection collection) {
        collection.setGroupId(cUserIDResolver.getID());
        return cConnection.send(new AddCollection(collection)).isSuccess();
    }

    /**
     * Gets the userdata from the server. Uses UserIDResolver to get the data. Behaviour depends on the state.
     *
     * @return All the userdata.
     */
    public PlayerData getPlayerData() {
        Response response = cConnection.send(new RequestPlayerData(cUserIDResolver.getID()));
        if (response.isSuccess()) {
            return (PlayerData) response.getData();
        }
        return new PlayerData(cUserIDResolver.getID());
    }

    /**
     * Stores the host ip on the server with a generated code that it will return to let the client connect.
     *
     * @return The code.
     */
    public Integer hostEvent() {
        IpResolver ipResolver = new IpResolver();
        try {
            Response response = cConnection.send(new RequestHostCode(ipResolver.getExternalIP()));
            if (response.isSuccess()) {
                return (Integer) response.getData();
            }
        } catch (UnknownHostException e) {
            return null;
        }
        return null;
    }

    /**
     * Retrieves the ip of the host.
     *
     * @param code code of the connection.
     * @return The hosts ip.
     */
    public String getHost(final Integer code) {
        Response response = cConnection.send(new RequestHostIp(code));
        if (response.isSuccess()) {
            return (String) response.getData();
        }
        return null;
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

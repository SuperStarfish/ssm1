package cg.group4.client;

import cg.group4.client.connection.Connection;
import cg.group4.client.connection.Unconnected;
import cg.group4.client.query.Data;
import cg.group4.client.query.UserData;
import cg.group4.rewards.Collection;
import cg.group4.util.timer.Timer;

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
    protected final String defaultIP = "127.0.0.1";

    /**
     * The default port to connect to.
     */
    protected final int defaultPort = 56789;

    /**
     * Constructs a new Client and sets the state to unconnected.
     */
    public Client() { cConnection = new Unconnected(); }

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

    /**
     * Updates the timers in the database. Behaviour depends on the state.
     *
     * @param timeStamp The timestamp from which to add the timer durations.
     */
    public void updateTimers(final long timeStamp) {
        UserData data = new UserData();
        data.setcID(cUserIDResolver.getID());
        data.setcIntervalTimeStamp(timeStamp + Timer.Global.INTERVAL.getDuration());
        data.setcStrollTimeStamp(timeStamp + Timer.Global.STROLL.getDuration());

        cConnection.updateUserData(data);
    }

    /**
     * Updates the username in the server. Behaviour depends on the state.
     * @param username The new username.
     * @return Successful or not.
     */
    public boolean updateUsername(final String username) {
        UserData data = new UserData();
        data.setcID(cUserIDResolver.getID());
        data.setcUsername(username);

        return cConnection.updateUserData(data);
    }

    /**
     * Adds the collection to the server. Behaviour depends on the state.
     * @param collection The collection to add to the server.
     * @return Successful or not.
     */
    public boolean updateCollection(final Collection collection) {
        boolean successful = cConnection.updateCollection(collection, new UserData(cUserIDResolver.getID()));
        System.out.println("Was it a succes?: " + successful);
        return successful;
    }

    /**
     * Gets the userdata from the server. Uses UserIDResolver to get the data. Behaviour depends on the state.
     * @return All the userdata.
     */
    public Data getUserData() {
        return cConnection.requestUserData(cUserIDResolver.getID());
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

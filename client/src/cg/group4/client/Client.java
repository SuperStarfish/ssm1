package cg.group4.client;

import cg.group4.client.connection.Connection;
import cg.group4.client.connection.UnConnected;
import cg.group4.data_structures.PlayerData;
import cg.group4.data_structures.collection.Collection;
import cg.group4.data_structures.subscribe.Subject;
import cg.group4.server.database.ResponseHandler;
import cg.group4.server.database.query.Query;
import cg.group4.server.database.query.RequestPlayerData;
import cg.group4.server.database.query.UpdateCollection;
import cg.group4.server.database.query.UpdatePlayerData;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author Jurgen van Schagen
 */
public final class Client {
    /**
     * Default Logger in Java used for the purpose of logging changes in the Server.
     */
    private static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    /**
     * The Client connection with the internal storage server.
     */
    protected static Client cLocalInstance;
    /**
     * The Client connection with the remote server.
     */
    protected static Client cRemoteInstance;
    /**
     * The default IP to connect to.
     */
    protected final String cDefaultIp = "82.169.19.191";
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
     * Notifies all listeners that the server has either connected or disconnected.
     */
    protected Subject cChangeSubject;

    /**
     * A list of Runnable that have to be run at the end of a render cycle.
     * These are added to the Gdx.app.postRunnable().
     */
    protected ArrayList<Runnable> cPostRunnables;

    /**
     * Determines if a request to the server has been made. If so, no new requests can be made.
     */
    protected boolean cAwaitingResponse = false;

    /**
     * Constructs a new Client and sets the state to unconnected.
     */
    public Client() {
        cConnection = new UnConnected();
        cUserIDResolver = new DummyUserIdResolver();
        cChangeSubject = new Subject();
        cPostRunnables = new ArrayList<Runnable>();
    }

    /**
     * Gets the Singleton instance.
     *
     * @return The Client.
     */
    public static Client getLocalInstance() {
        if (cLocalInstance == null) {
            cLocalInstance = new Client();
        }
        return cLocalInstance;
    }

    /**
     * Gets the Singleton instance.
     *
     * @return The Client.
     */
    public static Client getRemoteInstance() {
        if (cRemoteInstance == null) {
            cRemoteInstance = new Client();
        }
        return cRemoteInstance;
    }

    /**
     * Returns the ChangeSubject that notifies whenever a change in the remote connection status occurs.
     * @return Subject that can be subscribed on.
     */
    public Subject getChangeSubject() {
        return cChangeSubject;
    }

    /**
     * Returns the list of Runnable that need to be processed in the Gdx.app.postRunnable()
     * @return ArrayList with the Runnables.
     */
    public ArrayList<Runnable> getPostRunnables() {
        return cPostRunnables;
    }

    /**
     * Adds another Runnable to the PostRunnable list.
     */
    public void addPostRunnables(Runnable runnable) {
        cPostRunnables.add(runnable);
    }

    /**
     * Clears the PostRunnable ArrayList.
     */
    public void resetPostRunnables() {
        cPostRunnables.clear();
    }

    /**
     * Connects to the server. Behaviour depends on the state.
     */
    public void connectToServer() {
        if(!cAwaitingResponse) {
            cAwaitingResponse = true;
            cConnection.connect(cDefaultIp, cDefaultPort);
        }
    }

    /**
     * Connects to a server given an IP and port. If the server is null, it will connect to localhost.
     * @param ip Ip to connect to. If null localhost.
     * @param port Port to connect to.
     */
    public void connectToServer(String ip, int port) {
        if(!cAwaitingResponse) {
            cAwaitingResponse = true;
            cConnection.connect(ip, port);
        }
    }

    /**
     * Enables the client to take a new request.
     */
    public void enableRequests() {
        cAwaitingResponse = false;
    }

    /**
     * Sets the connection to the new connection.
     * @param connection The connection that needs to be set.
     */
    public void setConnection(final Connection connection) {
        LOGGER.info("Is connected: " + connection.isConnected());
        cConnection = connection;
        cChangeSubject.update(connection.isConnected());
        enableRequests();
    }

    /**
     * Updates the stroll timers in the database.
     *
     * @param timeStamp The timestamp when the timer should end.
     * @return Whether the query succeeded.
     */
    public boolean updateStrollTimer(final Long timeStamp, ResponseHandler responseHandler) {
        PlayerData playerData = new PlayerData(cUserIDResolver.getID());
        playerData.setStrollTimeStamp(timeStamp);

        return tryToSend(new UpdatePlayerData(playerData), responseHandler);
    }

    /**
     * Updates the interval timers in the database.
     *
     * @param timeStamp The timestamp when the timer should end.
     * @return Whether the query succeeded.
     */
    public boolean updateIntervalTimer(final Long timeStamp, ResponseHandler responseHandler) {
        PlayerData playerData = new PlayerData(cUserIDResolver.getID());
        playerData.setIntervalTimeStamp(timeStamp);

        return tryToSend(new UpdatePlayerData(playerData), responseHandler);
    }

    /**
     * Updates the username in the server. Behaviour depends on the state.
     *
     * @param username The new username.
     * @return Successful or not.
     */
    public boolean updatePlayer(final String username, ResponseHandler responseHandler) {
        PlayerData playerData = new PlayerData(cUserIDResolver.getID());
        playerData.setUsername(username);
        return tryToSend(new UpdatePlayerData(playerData), responseHandler);
    }

    /**
     * Adds the collection to the server. Behaviour depends on the state.
     *
     * @param collection The collection to add to the server.
     * @return Successful or not.
     */
    public boolean updateCollection(final Collection collection, ResponseHandler responseHandler) {
        collection.setGroupId(cUserIDResolver.getID());
        return tryToSend(new UpdateCollection(collection), responseHandler);
    }

    protected boolean tryToSend(final Query query, final ResponseHandler responseHandler) {
        if(!cAwaitingResponse) {
            cAwaitingResponse = true;
            cConnection.send(query, responseHandler);
        }
        return cAwaitingResponse;
    }

    /**
     * Gets the userdata from the server. Uses UserIDResolver to get the data. Behaviour depends on the state.
     *
     * @return All the userdata.
     */
    public boolean getPlayerData(ResponseHandler responseHandler) {
        return tryToSend(new RequestPlayerData(cUserIDResolver.getID()), responseHandler);
    }

    /**
     * Sets the userid using the device ID.
     *
     * @param idResolver Tool used for getting the proper device ID.
     */
    public void setUserIDResolver(final UserIDResolver idResolver) {
        cUserIDResolver = idResolver;
    }

    public String getUserID() {
        return cLocalInstance.cUserIDResolver.getID();
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

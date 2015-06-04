package cg.group4.client.connection;

import cg.group4.client.query.Data;
import cg.group4.client.query.UserData;
import cg.group4.rewards.Collection;

/**
 * Connection interface for either Connected and Unconnected.
 */
public interface Connection {
    /**
     * Connects to the server and given IP and port.
     * @param ip The IP to connect to.
     * @param port The port to connect to.
     * @return The new connection state.
     */
    Connection connect(String ip, int port);

    /**
     * Disconnects from the server.
     *
     * @return The new connection state.
     */
    Connection disconnect();

    /**
     * Requests userdata from the server.
     * @param id The id of the user to lookup.
     * @return The data of that user.
     */
    Data requestUserData(String id);

    /**
     * Updates the userdata in the server with the new data.
     * @param data The new userdata.
     * @return Successful or not.
     */
    boolean updateUserData(UserData data);

    /**
     * Adds the collection to the users collection in the server.
     *
     * @param collection The collection to add.
     * @param userData The user who the collection belongs to.
     * @return Successful or not.
     */
    boolean updateCollection(Collection collection, UserData userData);

    /**
     * Returns if currently connected to the server.
     * @return Connected or not.
     */
    boolean isConnected();
}

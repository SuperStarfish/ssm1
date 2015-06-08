package cg.group4.client.connection;

import cg.group4.server.database.ResponseHandler;
import cg.group4.server.database.query.Query;

/**
 * Connection interface for either Connected and Unconnected.
 */
public interface Connection {
    /**
     * Connects to the server and given IP and port.
     *
     * @param ip   The IP to connect to.
     * @param port The port to connect to.
     */
    void connect(String ip, int port);

    /**
     * Returns if currently connected to the server.
     *
     * @return Connected or not.
     */
    boolean isConnected();

    /**
     * Sends data to the server and receives a serializable.
     *
     * @param data Data to be sent to the server.
     */
    void send(Query data, final ResponseHandler responseHandler);
}

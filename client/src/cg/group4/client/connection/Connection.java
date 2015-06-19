package cg.group4.client.connection;

import cg.group4.client.Client;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;
import cg.group4.server.database.query.Query;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Connection interface for either Connected and Unconnected.
 */
public abstract class Connection {
    /**
     * Connects to the server and given IP and port.
     *
     * @param ip   The IP to connect to.
     * @param port The port to connect to.
     */
    public abstract void connect(String ip, int port);

    /**
     * Returns if currently connected to the server.
     *
     * @return Connected or not.
     */
    public abstract boolean isConnected();

    /**
     * Sends data to the server and receives a serializable.
     *
     * @param query           Data to be sent to the server.
     * @param responseHandler The task to execute once a reply is received completed.
     */
    public void send(Query query, final ResponseHandler responseHandler) {
        send(new ConnectionPacket(query, responseHandler));
    }

    /**
     * Handles a connection packet with the server.
     *
     * @param connectionPacket The connection packet to be handled.
     */
    public abstract void send(ConnectionPacket connectionPacket);

    /**
     * Handles the transfer of a single connection packet.
     *
     * @param connectionPacket The connection packet to be handled.
     * @param inputStream      The inputStream to retrieve the response.
     * @param outputStream     the output stream to send the query.
     */
    protected void sendPacket(final ConnectionPacket connectionPacket,
                              ObjectOutputStream outputStream,
                              ObjectInputStream inputStream) {
        try {
            outputStream.writeObject(connectionPacket.getQuery());
            outputStream.flush();
            final Response response = (Response) inputStream.readObject();
            Client.getInstance().addPostRunnables(new Runnable() {
                @Override
                public void run() {
                    if (connectionPacket.getResponseHandler() != null) {
                        connectionPacket.getResponseHandler().handleResponse(response);
                    }
                }
            });
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

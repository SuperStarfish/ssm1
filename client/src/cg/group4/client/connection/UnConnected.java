package cg.group4.client.connection;

import cg.group4.client.Client;
import cg.group4.server.database.Response;
import cg.group4.server.database.ResponseHandler;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

/**
 * A state where the client is not connected to a server.
 */
public class UnConnected implements Connection {
    /**
     * Default java logging functionality.
     */
    protected static final Logger LOGGER = Logger.getLogger(Connection.class.getName());
    /**
     * Localhost that can be used to connect to the local server.
     */
    protected static final String LOCALHOST = "127.0.0.1";
    /**
     * Boolean whether this connection is already trying to connect.
     */
    protected boolean cConnecting;

    /**
     * Creates a new state where the Client is Unconnected.
     */
    public UnConnected() {
        cConnecting = false;
    }

    /**
     * Connects to either a local or a remote server. Connects to localhost if ip is 'null'.
     * @param ip   The IP to connect to.
     * @param port The port to connect to.
     */
    @Override
    public void connect(final String ip, final int port) {
        if (!cConnecting) {
            if (ip == null) {
                localConnect(LOCALHOST, port);
            } else {
                remoteConnect(ip, port);
            }
        }
    }

    /**
     * Attempts to connect to the local server.
     * @param ip Localhost.
     * @param port Port provided.
     */
    protected void localConnect(final String ip, final int port) {
        cConnecting = true;
        try {
            LOGGER.info("Trying to connect to the local server");
            Connection connection = new LocalConnection(ip, port);
            Client.getLocalInstance().setConnection(connection);
        } catch (IOException e) {
            LOGGER.info("Connection failed!");
        }
        cConnecting = false;
    }

    /**
     *
     * @param ip
     * @param port
     */
    public void aquariumConnect(final String ip, final int port) {
        try {
            LOGGER.info("Trying to connect to the remote server (aquarium)");
            final Connection connection = new RemoteConnection(ip, port);
            Client.getRemoteInstance().setConnection(connection);
            cConnecting = false;
            System.out.println("REMOTE INSTANCE: " + Client.getRemoteInstance().toString());
        } catch (IOException e) {
            LOGGER.info("Connection failed!");
        }
    }

    /**
     * Connects to a remote server. Connection is done in a separate Thread as not to block the game.
     * @param ip The IP to connect to.
     * @param port The port to connect to.
     */
    protected void remoteConnect(final String ip, final int port) {
        cConnecting = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.info("Trying to connect to the remote server");
                    final Connection connection = new RemoteConnection(ip, port);
                    System.out.println("REMOTE INSTANCE: " + Client.getRemoteInstance());
                    Client.getRemoteInstance().addPostRunnables(new Runnable() {
                        @Override
                        public void run() {
                            Client.getRemoteInstance().setConnection(connection);
                            cConnecting = false;
                            System.out.println("REMOTE INSTANCE: " + Client.getRemoteInstance());
                        }
                    });

                } catch (IOException e) {
                    LOGGER.info("Failed to connect to remote server, retrying.");
                    cConnecting = false;
                    connect(ip, port);
                }
            }
        }).start();
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void send(final Serializable data, final ResponseHandler responseHandler) {
        if (responseHandler != null) {
            responseHandler.handleResponse(new Response(false, null));
        }
    }
}

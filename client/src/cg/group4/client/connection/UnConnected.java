package cg.group4.client.connection;

import cg.group4.client.Client;
import cg.group4.server.database.ResponseHandler;
import cg.group4.server.database.query.Query;

import java.io.IOException;
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
     * The client reference. Primarily used to make calls back to client if connection fails.
     */
    protected Client cClient;

    /**
     * Creates a new state where the Client is Unconnected.
     * @param client Reference to the client.
     */
    public UnConnected(final Client client) {
        cClient = client;
    }

    /**
     * Connects to either a local or a remote server. Connects to localhost if ip is 'null'.
     * @param ip   The IP to connect to.
     * @param port The port to connect to.
     */
    @Override
    public void connect(final String ip, final int port) {
        if (ip == null) {
            localConnect(LOCALHOST, port);
        } else {
            remoteConnect(ip, port);
        }
    }

    /**
     * Attempts to connect to the local server.
     * @param ip Localhost.
     * @param port Port provided.
     */
    protected void localConnect(final String ip, final int port) {
        try {
            LOGGER.info("Trying to connect to the local server");
            Connection connection = new LocalConnection(ip, port);
            Client client = Client.getLocalInstance();
            client.setConnection(connection);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.info("Trying to connect to the remote server");
                    final Connection connection = new RemoteConnection(ip, port);
                    cClient.addPostRunnables(new Runnable() {
                        @Override
                        public void run() {
                            cClient.setConnection(connection);
                        }
                    });
                } catch (IOException e) {
                    LOGGER.info("Connection failed!");
                }
            }
        }).start();
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public void send(final Query data, final ResponseHandler responseHandler) {
        cClient.enableRequests();
    }
}

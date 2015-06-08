package cg.group4.client.connection;

import cg.group4.server.database.Response;
import cg.group4.server.database.query.Query;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Connection state where no connection is made with the server.
 */
public final class LocalConnection extends Thread implements Connection {
    /**
     * Default java logging functionality.
     */
    protected static final Logger LOGGER = Logger.getLogger(Connection.class.getName());

    /**
     * Creates a new unconnected state.
     */
    public LocalConnection() {
        this.start();
    }


    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
    }

    @Override
    public Connection connect(final String ip, final int port) {
        LOGGER.info("Trying to connect to the server");
        try {
            LOGGER.info("Trying to connect to the server");
            Connection connection = new RemoteConnection(ip, port);

            LOGGER.info("Managed to connect!");

            return connection;
        } catch (IOException e) {
            LOGGER.info("Connection failed!");
        }
        return this;
    }

    @Override
    public Connection disconnect() {
        return this;
    }

    @Override
    public Response send(final Query data) {
        LOGGER.info("Local connection not yet implemented.");
        return new Response(false, null);
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}

package cg.group4.client.connection;

import cg.group4.client.Client;
import cg.group4.server.database.ResponseHandler;
import cg.group4.server.database.query.Query;

import java.io.IOException;
import java.util.logging.Logger;

public class UnConnected implements Connection{
    /**
     * Default java logging functionality.
     */
    protected static final Logger LOGGER = Logger.getLogger(Connection.class.getName());

    protected final String localHost = "127.0.0.1";

    @Override
    public void connect(String ip, int port) {
        if (ip == null) {
            ip = localHost;
            localConnect(ip, port);
        } else {
            remoteConnect(ip, port);
        }
    }

    protected void localConnect(String ip, int port) {
        try {
            LOGGER.info("Trying to connect to the local server");
            Connection connection = new LocalConnection(ip, port);
            Client client = Client.getLocalInstance();
            client.setConnection(connection);
        } catch (IOException e) {
            LOGGER.info("Connection failed!");
        }
    }

    protected void remoteConnect(final String ip, final int port) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    LOGGER.info("Trying to connect to the remote server");
                    final Connection connection = new RemoteConnection(ip, port);
                    final Client client = Client.getRemoteInstance();
                    client.addPostRunnables(new Runnable() {
                        @Override
                        public void run() {
                            client.setConnection(connection);
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
    public void send(Query data, ResponseHandler responseHandler) {

    }
}

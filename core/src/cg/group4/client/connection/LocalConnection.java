package cg.group4.client.connection;

import cg.group4.database.Response;
import cg.group4.database.query.Query;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Connection state where no connection is made with the server.
 */
public final class LocalConnection extends Thread implements Connection {
    /**
     * TAG used for logging.
     */
    private static final String TAG = LocalConnection.class.getSimpleName();

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
        System.out.println(Thread.currentThread().getName());
        try {
            Gdx.app.debug(TAG, "Trying to connect to the server");
            Connection connection = new RemoteConnection(ip, port);

            Gdx.app.debug(TAG, "Managed to connect!");

            return connection;
        } catch (GdxRuntimeException e) {
            Gdx.app.debug(TAG, "Connection failed!");
        }
        return this;
    }

    @Override
    public Connection disconnect() {
        return this;
    }

    @Override
    public Response send(Query data) {
        Gdx.app.debug(TAG, "Local connection not yet implemented.");
        return null;
    }

    @Override
    public boolean isConnected() {
        return false;
    }
}

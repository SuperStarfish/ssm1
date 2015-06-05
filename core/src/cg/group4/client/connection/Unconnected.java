package cg.group4.client.connection;

import cg.group4.client.query.NoReply;
import cg.group4.client.query.UserData;
import cg.group4.rewards.Collection;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * Connection state where no connection is made with the server.
 */
public final class Unconnected extends Thread implements Connection {
    /**
     * TAG used for logging.
     */
    private static final String TAG = Unconnected.class.getSimpleName();

    /**
     * Creates a new unconnected state.
     */
    public Unconnected() {
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
            Connection connection = new Connected(ip, port);

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
    public NoReply requestUserData(final String id) {
        return new NoReply("Not connected to the server!");
    }

    @Override
    public boolean updateCollection(final Collection collection, final UserData userData) {
        return false;
    }

    @Override
    public boolean updateUserData(final UserData data) { return false; }

    @Override
    public boolean isConnected() {
        return false;
    }
}
